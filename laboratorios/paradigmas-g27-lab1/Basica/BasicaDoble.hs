module Basica.BasicaDoble where
import Dibujo
import Interp
import Basica.Comun

data Basica = Triangulo | TrainguloVioleta

ejemplo :: Dibujo Basica
ejemplo = apilar 1 1 (basica Triangulo) (rotar (basica TrainguloVioleta))

interpBas :: Basica -> ImagenFlotante
interpBas Triangulo = triangulo
interpBas TrainguloVioleta = trianguloVioleta
