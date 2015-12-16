(ns smapreduce.client
  (:gen-class)
  (:require [clojure.tools.nrepl :as repl]
            [smapreduce.utils :as u]))

(defn map-client
  [port map-function list-values]
  (let [code (str (list 'map map-function (cons 'quote (list list-values))))]
    (println "Evaluating" code "on" port)
    (with-open [conn (repl/connect :port port)]
      (-> (repl/client conn 1000)
        (repl/message {:op "eval" :code code})
        (repl/response-values)))))

(defn get-full-list
  [map-function list-values worker-ports]
  (let [rr-map 
        (u/round-robin-map list-values worker-ports)]
    (reduce 
      concat 
      (map (fn [map-args] 
             (let [[worker-port worker-values-list] map-args]
               (let [ret-val (first (map-client worker-port 
                                                map-function 
                                                worker-values-list))]
                 (println "For" worker-port 
                          "values" worker-values-list 
                          "Returned list is" ret-val)
                 ret-val)))
           rr-map))))
