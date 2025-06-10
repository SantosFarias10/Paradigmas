module Basica.Test where
import Dibujo
import Interp
import Basica.Comun

data Basica = Triangulo | TrianguloVioleta

ejemplo :: Dibujo Basica
ejemplo = apilar 1 1 (basica Triangulo) (espejar(rotar45 (basica TrianguloVioleta)))

topo :: Dibujo Basica
topo =  rotar45 (basica Triangulo) 

interpBas :: Basica -> ImagenFlotante
interpBas Triangulo = triangulo
interpBas TrianguloVioleta = trianguloVioleta
