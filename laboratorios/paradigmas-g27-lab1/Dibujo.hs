module Dibujo ( Dibujo,
                comp,
                rotar45,
                rotar,
                r180,
                r270,
                espejar,
                basica,
                (.-.),
                (///),
                (^^^),
                apilar,
                juntar,
                encimar,
                cuarteto,
                encimar4,
                ciclar,
                mapDib,
                foldDib ) where
-- Esto para modularizar el codigo, solo exportando las
-- funciones constructoras. Ejercicio 1.a.2

-- Definir el lenguaje via constructores de tipo
data Dibujo a = Basica a 
              | Rotar (Dibujo a)
              | Rotar45 (Dibujo a)
              | Espejar (Dibujo a)
              | Apilar Float Float (Dibujo a) (Dibujo a)
              | Juntar Float Float (Dibujo a) (Dibujo a)
              | Encimar (Dibujo a) (Dibujo a)
              deriving(Eq, Show)

-- Composición n-veces de una función con sí misma.
comp :: (a -> a) -> Int -> a -> a
comp _ 0 x = x
comp f n x | n < 0 = error "n<0"
           | otherwise = f (comp f (n-1) x)

-- Rotaciones
rotar45 :: Dibujo a -> Dibujo a
rotar45 = Rotar45

rotar :: Dibujo a -> Dibujo a
rotar = Rotar

r180 :: Dibujo a -> Dibujo a
r180 = comp Rotar 2

r270 :: Dibujo a -> Dibujo a
r270 = comp Rotar 3

-- Funciones Constructoras

espejar :: Dibujo a -> Dibujo a
espejar = Espejar

-- Transfomar un valor de tipo a como una Basica.
basica :: a -> Dibujo a
basica = Basica

-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.
-- dibujo 
-- dibujo
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) = Apilar 1 1

apilar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar = Apilar

-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.
-- dibujo dibujo
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) = Juntar 1 1

juntar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar = Juntar

-- Superpone una figura con otra.
-- dibujo
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) = Encimar

encimar :: Dibujo a -> Dibujo a -> Dibujo a
encimar = Encimar

-- Dados cuatro dibujos las ubica en los cuatro cuadrantes.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto a b c d = (.-.) ((///) a b) ((///) c d)
-- cuarteto a b c d = (a /// b) .-. (c /// d)

-- Una dibujo repetido con las cuatro rotaciones, superpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 a = (^^^) ((^^^) a (Rotar a)) ((^^^) (r180 a) (r270 a))
-- encimar4 a = (a ^^^ (Rotar a)) ^^^ ((r180 a) ^^^ (r270 a))

-- Cuadrado con la misma figura rotada i * 90, para i ∈ {0, ..., 3}.
-- No confundir con encimar4!
ciclar :: Dibujo a -> Dibujo a
ciclar a = cuarteto a (r270 a) (Rotar a) (r180 a)  
-- L: Cambia algo aparte de la rotación con encimar 4?
-- Diría que si solamente rotamos una figura, va a quedar encimada aunque 
-- no usemos el "apilar"

-- map para nuestro lenguaje.
mapDib :: (a -> b) -> Dibujo a -> Dibujo b
mapDib f (Basica a)           = Basica (f a)
mapDib f (Rotar a)            = Rotar (mapDib f a)
mapDib f (Rotar45 a)          = Rotar45 (mapDib f a)
mapDib f (Espejar a)          = Espejar (mapDib f a)
mapDib f (Apilar x y a b)     = Apilar x y (mapDib f a) (mapDib f b) 
mapDib f (Juntar x y a b)     = Juntar x y (mapDib f a) (mapDib f b)
mapDib f (Encimar a b)        = Encimar (mapDib f a) (mapDib f b)
 
-- Funcion de fold para Dibujos a
foldDib :: (a -> b)  -- f
        -> (b -> b)  -- rot
        -> (b -> b)  -- rot45
        -> (b -> b)  -- espej
        -> (Float -> Float -> b -> b -> b) -- apil
        -> (Float -> Float -> b -> b -> b) -- junt
        -> (b -> b -> b) -> -- encim
       Dibujo a -> b
{- Recibe 220 entradas porque se aplica c/u dependiendo de la fi-
   gura que reciba como último parámetro (O sea, el "Dibujo a")
   (Esto va a quedar re feo 😭)
   Post implementación: No quedó tan feo. 
-}

foldDib f rot rot45 espej apil junt encim (Basica a)  
        = f a

foldDib f rot rot45 espej apil junt encim (Rotar a)   
        = rot (foldDib f rot rot45 espej apil junt encim a)

foldDib f rot rot45 espej apil junt encim (Rotar45 a) 
        = rot45 (foldDib f rot rot45 espej apil junt encim a)

foldDib f rot rot45 espej apil junt encim (Espejar a) 
        = espej (foldDib f rot rot45 espej apil junt encim a)

foldDib f rot rot45 espej apil junt encim (Apilar x y c d) 
        = apil x y (foldDib f rot rot45 espej apil junt encim c) 
          (foldDib f rot rot45 espej apil junt encim d)

foldDib f rot rot45 espej apil junt encim (Juntar x y c d) 
        = junt x y (foldDib f rot rot45 espej apil junt encim c) 
          (foldDib f rot rot45 espej apil junt encim d)

foldDib f rot rot45 espej apil junt encim (Encimar c d) 
        = encim (foldDib f rot rot45 espej apil junt encim c) 
          (foldDib f rot rot45 espej apil junt encim d)


{- Detalle:
   En estas últimas funciones preferí usar 'c' y 'd' para las figuras para 
   evitar confusiones, ya que 'b' es el parámetro que devuelve 'f'
   y no uno de los valores que recibe "foldDib"
-}

{- Explicación del quilombo:
   Hay recursión por cada caso posible de Dibujo, o sea, uno para "Rotar"
   otro para Rotar45, etc, etc. (Para "Basica" no hay recursión ya que sería
   el caso base)
   Las funciones de tipo "Fold" devuelven un único valor a partir de varios 
   elementos. En este caso, 'foldLib', dado un "Dibujo a",
   "viaja" recursivamente hasta llegar a "Básica a".
   Cuando ocurre eso, aplica una función ('f' en los parámetros de 
   'foldDib') y empieza a "subir".
   A medida que va subiendo se aplican las funciones designadas a través de la 
   recursión hecha previamente.
-}

 {- Ejemplo fiacoso de ejecución:
   Se quiere calcular algo respecto a un dibujo con la estructura: Espejar (Rotar45 (Apilar (Juntar (Basica a) )))
  
   GHCI: foldDib f rot rot45 espej apil junt encim (Espejar (Rotar45 (Apilar (Juntar (Basica a) ))))
  
   Lo que se va a ir obteniendo recursivamente va a ser:
   1:
   espej (foldDib f [...] (Rotar45 (Apilar (Juntar (Basica a) )))
   espej (rot45 (apil x y (foldDib f [...] (Juntar (Basica a) )))
   espej (rot45 (apil x y (junt x y (foldDib f [...] (Basica a) )))
   espej (rot45 (apil x y (junt x y (f a)))
 -} 

 {- Se puede decir que ahora "empieza a subir"
   2:
   espej (rot45 (apil x y (junt x y b)))
   espej (rot45 (apil x y b))
   espej (rot45 b)
   espej b
   b
   ('b' fue cambiando a medida que se le aplicaban las definiciones de 'apil',
   'rot45 y 'junt'. No hice nada porque solo es saber que es del mismo tipo.
   Más abajo dejo un ejemplo donde va cambiando.
 -}

 {- Supongan que todas las funciones que se le pasaron a foldDib suman 1 a 
   'b'. 
   Se podría decir que esta función calcula la "profundidad" de un dibujo
   Repitiendo el ejemplo de arriba, se vería algo como:
   Se repite 1:
   2:
   espej (rot45 (apil x y (junt x y 1)))
   espej (rot 45 (apil x y 2)))
   espej (rot45 (3)
   espej 4
   5
   Como el dibujo que se le pasó a foldDib era Espejar (Rotar45 (Apilar (Juntar (Basica a))))
   se puede ver que este tenía una "profundidad" de 5.
 -}

