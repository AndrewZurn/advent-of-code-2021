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

(defn calculate-next-position [next-direction next-value horizontal depth]
  (if (= "forward" next-direction)
    {:horizontal (+ horizontal next-value) :depth depth}
    (if (= "up" next-direction)
      {:horizontal horizontal :depth (- depth next-value)}
      ;; else down
      {:horizontal horizontal :depth (+ depth next-value)})))

(defn calculate-next-position-with-aim [next-direction next-value horizontal depth aim]
  (if (= "forward" next-direction)
    {:horizontal (+ horizontal next-value) :depth (+ depth (* aim next-value)) :aim aim}
    (if (= "up" next-direction)
      {:horizontal horizontal :depth depth :aim (- aim next-value)}
      ;; else down
      {:horizontal horizontal :depth depth :aim (+ aim next-value)})))

(defn answer-it [coordinates with-aim]
  (reduce (fn [accum elem]
            (let [horizontal (:horizontal accum)
                  depth (:depth accum)
                  aim (:aim accum 0)                        ;; default 0
                  next-direction (:direction elem)
                  next-value (:value elem)]
              (if (true? with-aim)
                (calculate-next-position-with-aim next-direction next-value horizontal depth aim)
                (calculate-next-position next-direction next-value horizontal depth))))
          {:horizontal 0 :depth 0 :aim 0}                   ;; initial element
          coordinates))

(def answer-one (answer-it parse-data false))
(def answer-two (answer-it parse-data true))
(println "Part 1 total was: " (* (:horizontal answer-one) (:depth answer-one)))
(println "Part 2 total was: " (* (:horizontal answer-two) (:depth answer-two)))
