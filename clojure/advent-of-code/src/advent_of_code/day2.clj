(ns advent-of-code.day2
  (:require [advent-of-code.core :as c]))

(def data (c/parse-file "day2.txt"))

(def parse-data
  (map
    (fn [elem]
      (let [split (.split elem " ")]
        {:direction (first split)
         :value     (Integer/parseInt (last split))}))
    data))

(defn answer-it [coordinates]
  (reduce (fn [accum elem]
            (let [horizontal (:horizontal accum)
                  depth (:depth accum)
                  next-direction (:direction elem)
                  next-value (:value elem)]
              (if (= "forward" next-direction)
                {:horizontal (+ horizontal next-value) :depth depth}
                (if (= "up" next-direction)
                  {:horizontal horizontal :depth (- depth next-value)}
                  {:horizontal horizontal :depth (+ depth next-value)}))))
          {:horizontal 0 :depth 0}                          ;; initial element
          coordinates))

(def answer-one (answer-it parse-data))
(println "Part 1 total was: " (* (:horizontal answer-one) (:depth answer-one)))
