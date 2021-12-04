(ns advent-of-code.day3
  (:require [advent-of-code.core :as c]))

(def data (c/parse-file "day3.txt"))

(def input-length (alength data))
(def elem-range (range 0 (.length (first data))))
(println elem-range)

;; This will be a map that will keep the tally of 1 seen in the input for each index in our input strings
;; ie. 0101 and 0110 will be counted as {:0 0 :1 2 :2 1 :3 1}
(def result-atom (atom (into {} (map (fn [index] {index 0}) elem-range))))

;; sum, returning a map of the summed inputs per index
(defn sum-inputs [data]
  (map
    (fn [elem]
      ;; each element in the input string (ie. 0, 1, 0, 1 from 0101) plus the index where we are at in the string
      (map-indexed
        (fn [idx num]
          ;; update the current index with the current value in the index plus this num
          (swap! result-atom
                 (fn [result] (update result idx (fn [tally] (+ tally (Integer/parseInt (String/valueOf num))))))))
        elem))
    data))

;; something weird going on with laziness that I need to trigger a side affect to get sum-inputs to write to result-atom correctly :/
(println "Intermediate summed results are:" (sum-inputs data))
(println "Intermediate results-atom is:" @result-atom)

;; ie. ["1" "0" "1"...]
(defn get-readings [is-gamma]
  (clojure.string/join
    (map
      (fn [idx]
        (let [result-at-index (get @result-atom idx)]
          (if (true? is-gamma)
            (if (>= result-at-index (/ input-length 2)) "1" "0") ;; gamma
            (if (< result-at-index (/ input-length 2)) "1" "0")))) ;; epsilon
      elem-range)))

(def gamma-reading (get-readings true))
;; technically just the 'flip' gamma reading, but I'm too lazy to figure out the clojure bit mechanics
(def epsilon-reading (get-readings false))

(println "Gamma Binary:" gamma-reading)
(println "Epsilon Binary:" epsilon-reading)

(println "Part 1 result was:" (* (Integer/parseInt gamma-reading 2) (Integer/parseInt epsilon-reading 2)))
