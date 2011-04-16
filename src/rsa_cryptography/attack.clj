(ns rsa-cryptography.attack
  (:use [rsa-cryptography.core] :reload))

(defn rand-big-int [max]
  (new BigInteger (dec (bit-length max)) (new java.util.Random)))

(defn message-round-trip
  [keys message]
  (decrypt-message (encrypt-message message (:e keys) (:modulus keys))
                   (:d keys) (:modulus keys)))

(defn unchanged-round-trip
  ([] (unchanged-round-trip (generate-keys 16)))
  ([keys] (unchanged-round-trip keys (rand-big-int (:modulus keys))))
  ([keys message] (= message (message-round-trip keys message))))

(defn generate-keys-list
  "Creates a list of keys based on _keys_, incrementing :d from _d-start_"
  ([] (generate-keys-list (generate-keys 16)))
  ([keys] (generate-keys-list keys 0))
  ([keys d-start]
     (map #(assoc keys :d %) (iterate inc d-start))))

(defn check-solution-keys
  "Checks if any keys in the list encryt/decrypt a message successfully"
  [keys-list]
  (some unchanged-round-trip keys-list))
