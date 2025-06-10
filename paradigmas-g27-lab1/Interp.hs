-- Sacar del esqueleto final!
module Interp where
import Graphics.Gloss
import Graphics.Gloss.Data.Vector
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import Dibujo

-- Gloss provee el tipo Vector y Picture.
type ImagenFlotante = Vector -> Vector -> Vector -> Picture
type Interpretacion a = a -> ImagenFlotante

mitad :: Vector -> Vector
mitad = (0.5 V.*)

-- Interpretaciones de los constructores de Dibujo

--interpreta el operador de rotacion
interp_rotar :: ImagenFlotante -> ImagenFlotante
interp_rotar f d w h = f (d V.+ w) h (V.negate w)

--interpreta el operador de espejar
interp_espejar :: ImagenFlotante -> ImagenFlotante
interp_espejar f d w h = f (d V.+ w) (V.negate w) h

--interpreta el operador de rotacion 45
interp_rotar45 :: ImagenFlotante -> ImagenFlotante
interp_rotar45 f d w h = f (d V.+ mitad (w V.+ h)) (mitad (w V.+ h)) (mitad (h V.+ (V.negate w)))

--interpreta el operador de apilar
interp_apilar :: Float -> Float -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_apilar m n f g d w h = let r'= n/(m+n)
                                  r = m/(m+n)
                                  h'= r' V.* h
                              in Pictures [f (d V.+ h') w (r V.* h), g d w h']

 {-- Function explanation: (I won't explain enunciado's formulas)
 -- I used enunciado's names to avoid confusions, so it's all pretty much the same.
 -- 'let' is used to assign local variables and i's implicit in vertically
 -- aligned columns.
 -- I literally spammed 'fromIntegral' because {Imagine I explained it REALLY
 -- well, I'm kinda tired of this right now}
 -- in expresion "f d+h' w r*h", haskell automatically knows that
 -- second argument (d+h') is the first ImagenFlotante's value.
 -- Same for every other parameter. For 'g'. Even with the same variables names,
 -- haskell can distinguish between they because 'f' and 'g' are used before them.
 --}

--interpreta el operador de juntar
interp_juntar :: Float -> Float -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_juntar m n f g d w h = let r'= n/(m+n)
                                  r = m/(m+n)
                                  w'= r V.* w
                              in Pictures [f d w' h, g (d V.+ w') (r' V.* w) h]

--interpreta el operador de encimar
interp_encimar :: ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_encimar f g v1 v2 v3 = Pictures [f v1 v2 v3, g v1 v2 v3]

{- This last one might be hard to explain, but I'll try to do it anyway.
 Also, I'm not sure if this implementation is correct.
 Link to documentation: https://wiki.haskell.org/Currying
 Haskell has a weird concept called curried functions.
 It means that some functions convert their arguments into tuples.

 Docs Example: f :: a -> b -> c is the "curried" version of f :: (a,b) -> c

 Since there is "currying", there is also "uncurrying", which is the opposite
 of currying. It's like going backwards.
 Example: if we "uncurry" the function f :: (a,b) -> c it would become f :: a -> b -> c.
 So, this would make this function's prototype look something like:
 interp_encimar :: (ImagenFlotante, ImagenFlotante) -> (Vector -> Vector -> Vector -> Picture) -> ImagenFlotante
 This way, I can pass up to 5 arguments in its implementation.
-}

--interpreta cualquier expresion del tipo Dibujo a
--utilizar foldDib
interp :: Interpretacion a -> Dibujo a -> ImagenFlotante
interp a = foldDib a interp_rotar interp_rotar45 interp_espejar interp_apilar interp_juntar interp_encimar
-- I just passed every function defined in this file. Hope that works.

