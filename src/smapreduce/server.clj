(ns smapreduce.server
  (:gen-class)
  (:require [clojure.tools.nrepl.server :as server]
            [clojure.tools.nrepl.middleware.pr-values :as pv]))

(defn repl-server
  [port]
  (println "Created REPL server on port" port)
  (server/start-server :port port 
                       :handler (server/default-handler 
                                  #'pv/pr-values)))
