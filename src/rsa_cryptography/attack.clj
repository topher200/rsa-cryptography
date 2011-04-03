(ns rsa-cryptography.attack
  (:use [rsa-cryptography.core] :reload))

(defn message-round-trip
  [message]
  (let [keys (generate-keys 16)]
    (decrypt-message (encrypt-message message (:e keys) (:modulus keys))
                     (:d keys) (:modulus keys))))

(defn unchanged-round-trip
  ([] (unchanged-round-trip (rand-int 25600)))
  ([message] (= message (message-round-trip message))))

