module Dibujo where

-- Definicion  del lenguaje
data Dibujo a = Basica a 
              | Rotar (Dibujo a)
              | Apilar Int Int (Dibujo a) (Dibujo a)
              | Encimar (Dibujo a) (Dibujo a)
              deriving(Show, Eq)


-- Funcion Map (de Basicas) para nuestro sub-lenguaje.
mapDib :: (a -> b) -> Dibujo a -> Dibujo b
mapDib f (Basica x) = Basica (f x) 
mapDib f (Rotar d1) = Rotar (mapDib f d1)
mapDib f (Apilar n m d1 d2) = Apilar n m (mapDib f d1) (mapDib f d2)
mapDib f (Encimar d1 d2) = Encimar (mapDib f d1) (mapDib f d2)


-- Funcion Fold para nuestro sub-lenguaje.
foldDib :: (a -> b) -> (b -> b) ->
       (Int -> Int -> b -> b -> b) -> 
       (b -> b -> b) ->
       Dibujo a -> b

foldDib sB sR sA sEn d =
    let foldDibRecursiva = foldDib sB sR sA sEn sRe
    in case d of
        Basica x -> sB x
        Rotar d -> sR $ foldDibRecursiva d
        Apilar m n d1 d2 -> sA m n (foldDibRecursiva d1) (foldDibRecursiva d2)
        Encimar d1 d2 -> sEn (foldDibRecursiva d1) (foldDibRecursiva d2)



--COMPLETAR (EJERCICIO 1-a)
toBool:: Dibujo (Int,Int) -> Dibujo Bool


--COMPLETAR (EJERCICIO 1-b)
toBool2:: Dibujo (Int,Int) -> Dibujo Bool


--COMPLETAR (EJERCICIO 1-c)
profundidad:: Dibujo a -> Int


--COMPLETAR (EJERCICIO 1-d)
profundidad2:: Dibujo a -> Int

