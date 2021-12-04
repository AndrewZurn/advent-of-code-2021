(ns advent-of-code.core
  (:require [clojure.java.io :as io]))

(defn parse-file [filename]
  (.split (slurp (io/resource filename)) "\n"))
