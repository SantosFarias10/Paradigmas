module Pred where

import Dibujo
import GHC.Core (otherCons)

-- `Pred a` define un predicado sobre figuras básicas. Por ejemplo,
-- `(== Triangulo)` es un `Pred TriOCuat` que devuelve `True` cuando la
-- figura es `Triangulo`.
type Pred a = a -> Bool

-- Para las definiciones de la funciones de este modulo, no pueden utilizar
-- pattern-matching, sino alto orden a traves de la funcion foldDib, mapDib

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por el resultado de llamar a la función indicada por el
-- segundo argumento con dicha figura.
-- Por ejemplo, `cambiar (== Triangulo) (\x -> Rotar (Basica x))` rota
-- todos los triángulos.
cambiar :: Pred a -> (a -> a) -> Dibujo a -> Dibujo a
cambiar p f = mapDib (\x -> if p x then f x else x)

-- Alguna básica satisface el predicado.
anyDib :: Pred a -> Dibujo a -> Bool
anyDib p = foldDib p id id id (\n m dib1 dib2 -> dib1 || dib2) (\n m dib1 dib2 -> dib1 || dib2) (||)

-- Todas las básicas satisfacen el predicado.
allDib :: Pred a -> Dibujo a -> Bool
allDib p = foldDib p id id id (\n m dib1 dib2 -> dib1 && dib2) (\n m dib1 dib2 -> dib1 && dib2) (&&)

-- Hay 4 rotaciones seguidas.
-- this is an alias for esRot360 :: Dibujo a -> bool
esRot360 :: Pred (Dibujo a)
esRot360 d = 4 <= foldDib (\x -> 0)   -- Basica a sets the counter to 0.
                          (\x -> x+1) -- Rot sums 1 to the counter.
                          (\x -> if x >= 4 then 4 else 0)   -- Rot45 resets.1*
                          (\x -> if x >= 4 then 4 else 0)   -- same for Espejar.
                          (\a b c d -> if max c d >= 4 then 4 else 0)  -- 2*
                          (\a b c d -> if max c d >= 4 then 4 else 0)  -- 2*
                          (\a b -> if (a >= 4) || (b >= 4) then 4 else 0)
                          d
           -- 1*: except if counter is already 4
           -- 2*: take max value between both figures, otherwise reset counter.


-- Hay 2 espejados seguidos.
esFlip2 :: Pred (Dibujo a)
esFlip2 d = 2 <= foldDib (\x -> 0)
                         (\x -> if x >= 2 then 2 else 0)
                         (\x -> if x >= 2 then 2 else 0)
                         (\x -> x+1)
                         (\a b c d -> if max c d >= 2 then 2 else 0)
                         (\a b c d -> if max c d >= 2 then 2 else 0)
                         (\a b -> if (a >= 2) || (b >= 2) then 2 else 0)
                         d

data Superfluo = RotacionSuperflua | FlipSuperfluo deriving(Show)

-- Chequea si el dibujo tiene una rotacion superflua
errorRotacion :: Dibujo a -> [Superfluo]
errorRotacion d | esRot360 d = [RotacionSuperflua]
                | otherwise = []

-- Chequea si el dibujo tiene un flip superfluo
errorFlip :: Dibujo a -> [Superfluo]
errorFlip d | esFlip2 d = [FlipSuperfluo]
            | otherwise = []

-- Aplica todos los chequeos y acumula todos los errores, y
-- sólo devuelve la figura si no hubo ningún error.
checkSuperfluo :: Dibujo a -> Either [Superfluo] (Dibujo a)
checkSuperfluo d | esRot360 d || esFlip2 d = Left (errorRotacion d ++ errorFlip d)
                 | otherwise = Right d
