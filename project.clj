(defproject smapreduce "0.0.1-SNAPSHOT"
  :description "Simple MapReduce implementation"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.nrepl "0.2.11"]]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [smapreduce.core
        smapreduce.server]
  :main smapreduce.core)
