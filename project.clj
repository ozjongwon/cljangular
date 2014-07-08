(defproject com.ozjongwon/cljangular "1.0.0-SNAPSHOT"
  :author "Jong-won Choi"
  :description "Macros for AngularJS programming in ClojureScript"
  :url "https://github.com/ozjongwon/cljangular"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}
  :min-lein-version "2.3.3"
  :global-vars {*warn-on-reflection* true
                *assert* true}
  :dependencies
  [[org.clojure/clojure         "1.6.0"]]
  :jvm-opts ["-Xmx512m"
             "-XX:MaxPermSize=256m"
             "-XX:+UseParNewGC"
             "-XX:+UseConcMarkSweepGC"
             "-Dfile.encoding=UTF-8"
             "-Dsun.jnu.encoding=UTF-8"
             "-Dsun.io.useCanonCaches=false"]
  :encoding "utf-8"
  )
