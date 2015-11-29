(ns smapreduce.core
  (:gen-class)
  (:require [smapreduce.client :as c]
            [smapreduce.server :as s]))

(defn run-map-or-reduce
  ([command & args]
    (println "Command:" command "for Arguments:" args)
    (case command
      "map" (s/repl-server (read-string (first args)))
      "reduce" (let [[reduce-function map-function list-values & worker-ports] (vec (map read-string args))]
                 (let [full-list 
                       (let [grouped-values 
                             (group-by first (mapv 
                                               #(list %1 %2) 
                                               (cycle worker-ports) 
                                               list-values))]
                         (reduce 
                           concat 
                           (map (fn [map-args] 
                                  (let [worker-port (first map-args)
                                        worker-values-list (map second (second map-args))]
                                    (let [ret-val (first (c/map-client worker-port 
                                                                       map-function 
                                                                       worker-values-list))]
                                      (println "For" worker-port 
                                               "values" worker-values-list 
                                               "Returned list is" ret-val)
                                      ret-val)))
                                grouped-values)))]
                   (println "Full list is" full-list)
                   (let [reduced-value (reduce (eval reduce-function) full-list)]
                     (println "Reduced value is" reduced-value))))))

  ([]
    (println "Use map port or reduce port arguments")))

(defn -main
  [& args]
  (apply run-map-or-reduce args))
