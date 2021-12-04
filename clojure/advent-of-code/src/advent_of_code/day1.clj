(ns advent-of-code.day1
  (:require [advent-of-code.core :as c]))

(def data (c/parse-file "day1.txt"))

(def parsed-data (map #(Integer/parseInt %) data))

(defn answer-it-all [nums]
  (reduce
    (fn [accum elem]
      (let [previous-elem (:previous-elem accum)
            total (:total accum)]
        (if (> elem previous-elem)
          {:total (+ total 1) :previous-elem elem}
          {:total total :previous-elem elem})))
    {:total 0 :previous-elem 999999}                        ;; initial element, previous-elem should be a large number so we don't count the first comparison (ie any number is always greater than 0/null)
    nums))

(def grouped-inputs-summed
  (map
    (fn [group] (reduce + group))
    (partition 3 1 parsed-data)))

(println "Part 1 total was:" (:total (answer-it-all parsed-data)))
(println "Part 2 total was:" (:total (answer-it-all grouped-inputs-summed)))
