;;;;   -*- Mode: clojure; encoding: utf-8; -*-
;;
;; Copyright (C) 2014 Jong-won Choi
;; All rights reserved.
;;
;;;; Commentary:
;;
;;
;;
;;;; Code:

(ns cljangular.core
    (:require [clojure.string :as s]))

(defmacro def.module [name & requires]
  `(js/angular.module ~(str name)
                      (~'array ~@(when requires
                                   (map str requires)))))

(defmacro def.config [module [& providers] & body]
  `(-> (js/angular.module ~(str module))
       ;; Inline Array Annotation
       ;; See https://docs.angularjs.org/guide/di
       (.config (~'array ~@(map str providers)
                         (fn [~@providers]
                           ~@body
                           nil)))))

(defmacro angular-factory [fac-fn module:fac injections body & return-value]
  (let [[module fac] (-> (str module:fac) (s/split #":"))]
    (assert (and module fac) "Must be <module>:<factory-name>")
    `(-> (js/angular.module ~module)
         ;; Inline Array Annotation
         ;; See https://docs.angularjs.org/guide/di
         (~fac-fn ~fac (~'array ~@(map str injections)
                            (fn [~@injections]
                              ~@body
                              ~@return-value))))))

(defmacro def.controller [module:ctrl [& injections] & body]
  `(angular-factory .controller ~module:ctrl ~injections ~body nil))

(defmacro def.directive [module:directive [& injections] & body]
  `(angular-factory .directive ~module:directive ~injections ~body))

(defmacro def.filter [module:filter [& injections] & body]
  `(angular-factory .filter ~module:filter ~injections ~body))

(defmacro def.service [module:service [& injections] & body]
  `(angular-factory .service ~module:service ~injections ~body))

(defmacro def.factory [module:factory [& injections] & body]
  `(angular-factory .factory ~module:factory ~injections ~body))

(defmacro def.provider [module:provider [& injections] & body]
  `(angular-factory .provider ~module:provider ~injections ~body))

(defmacro angular-var [fac-fn module:var val]
  (let [[module var] (-> (str module:var) (s/split #":"))]
    (assert (and module var) "Must be <module>:<var-name>")
    `(-> (js/angular.module ~module)
         (~fac-fn ~var ~val))))

(defmacro def.constant [module:constant val]
  `(angular-var .constant ~module:constant ~val))

(defmacro def.value [module:var val]
  `(angular-var .value ~module:var ~val))

(defmacro def.run [module & defs]
  `(do
     ~@(map (fn [[params & body]]
              `(-> (js/angular.module ~(str module))
                   (.run (fn ~params ~@body nil))))
            defs)))

;;; CORE.CLJ ends here
