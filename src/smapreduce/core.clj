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
                 (let [full-list (c/get-full-list 
                                   map-function 
                                   list-values 
                                   worker-ports)]
                   (println "Full list is" full-list)
                   (let [reduced-value (reduce (eval reduce-function) full-list)]
                     (println "Reduced value is" reduced-value))))))

  ([]
    (println "Use map port or reduce port arguments")))

(defn -main
  [& args]
  (apply run-map-or-reduce args))
