(ns smapreduce.client
  (:gen-class)
  (:require [clojure.tools.nrepl :as repl]))

(defn map-client
  [port map-function list-values]
  (let [code (str (list 'map map-function (cons 'quote (list list-values))))]
    (println "Evaluating" code "on" port)
    (with-open [conn (repl/connect :port port)]
      (-> (repl/client conn 1000)
        (repl/message {:op "eval" :code code})
        (repl/response-values)))))
