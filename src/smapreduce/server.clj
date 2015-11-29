(ns smapreduce.server
  (:gen-class)
  (:require [clojure.tools.nrepl.server :as server]))

(defn repl-server
  [port]
  (println "Created REPL server on port" port)
  (server/start-server :port port))
