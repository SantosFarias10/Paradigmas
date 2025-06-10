module Basica.Ejemplo where
import Dibujo
import Interp
import Basica.Comun

type Basica = ()

ejemplo :: Dibujo Basica
ejemplo = rotar(basica ())

interpBas :: Basica -> ImagenFlotante
interpBas () = formaF
