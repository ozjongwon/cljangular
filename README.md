# Cljangular, macros for AngularJS programming in ClojureScript

After playing with gyr(https://github.com/purnam/gyr), I decided to write some helper macros for AngularJS programming in ClojureScript.

## Usage

### init
```clojure
[com.ozjongwon/dynohub "1.0.0-SNAPSHOT"]              ; project.clj


(ns my-app (:use-macros [cljangular.core :only [def.module	def.config
						def.run		def.controller
					  	def.constant	def.value
						def.directive	def.filter
						def.factory	def.service
				  		def.provider]]))

```

### def.module
```clojure
(def.module name & requires)

(def.module sportsStoreAdmin ngRoute ngResource)
```

### def.config
```clojure
(def.config name [& providers] & body)

(def.config sportsStore [$routeProvider]
  (doto $routeProvider
    (.when "/complete" (js-obj "templateUrl" "/html/thank-you.html"))
    (.when "/placeorder" (js-obj "templateUrl" "/html/place-order.html"))
    (.when "/checkout" (js-obj "templateUrl" "/html/checkout-summary.html"))
    (.when "/products" (js-obj "templateUrl" "/html/product-list.html"))
    (.otherwise (js-obj "templateUrl" "/html/product-list.html"))))
```

### def.run
```clojure
(def.run module & defs)

(def.run todoApp
  ([$http] (-> $http
               (.get "todo.json")
               (.success (fn [data]
                           (set! model.items data)))))
  ;; Just an example of multiple runs!
  ([] (js/console.log ">>> !!OK!! <<<")))
```

### def.controller
```clojure
(def.controller module-name:controller-name [& injections] & body)

(def.controller sportsStore:sportsStoreCtrl [$scope $http $location dataUrl orderUrl cart]
  (set! (. $scope -data) (js-obj)))

```

### def.constant
```clojure
(def.constant module-name:constant-name val)

(def.constant todoApp:cont1 10)
```

### def.value
```clojure
(def.value module-name:var-name val)

(def.value todoApp:val1 20)
```

### def.directive
```clojure
(def.directive module-name:directive-name [& injections] & body]

(def.directive cart:cartSummary [cart]
  (js-obj "restrict" "E"
          "templateUrl" "/html/cart-summary.html"
          "controller" (fn [$scope]
                         (let [cart-data (cart.getProducts)]
                           (aset $scope "total" (fn [] (aget cart "productsTotal")))
                           (set! (. $scope -itemCount) (fn []
                                                         (loop [i 0 total 0]
                                                           (if (>= i (. cart-data -length))
                                                             total
                                                             (let [item (aget cart-data i)]
                                                               (recur (inc i)
                                                                      (+ total (. item -count))))))))))))
```

### def.filter
```clojure
(def.filter module-name:filter-name [& injections] & body]

(def.filter customFilters:unique []
  (fn [data pname]
    (if (and (array? data) (string? pname))
      (let [result (array)
            key-map (js-obj)]
        (doseq [el data]
          (let [v (aget el pname)]
            (when-not (= (aget key-map v) true)
              (aset key-map v true)
              (result.push v))))
        result)
      data)))
```

### def.factory
```clojure
(def.factory module-name:factory-name [& injections] & body]

(def.factory cart:cart []
  (let [cart-data (array)]
    (js-obj "addProduct" (fn [id name price]
                        (loop [i 0]
                          (if (>= i cart-data.length)
                            (cart-data.push (js-obj "count" 1 "id" id "price" price "name" name))
                            (let [record (aget cart-data i)]
                              (if (= (aget record "id") id)
                                (aset record "count" (inc (aget record "count")))
                                (recur (inc i))))))))))
```

### def.service
```clojure
(def.service module-name:service-name [& injections] & body]
```

### def.provider
```clojure
(def.provider module-name:provider-name [& injections] & body]
```


## License


Copyright &copy; 2014 Jong-won Choi. Distributed under the [Eclipse Public License][], the same as Clojure.



[Eclipse Public License]: <https://raw2.github.com/ozjongwon/cljangular/master/LICENSE>