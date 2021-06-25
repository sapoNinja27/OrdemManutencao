package main.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.domain.enums.TipoUsuario;
/**
*Endpoint para cargos
*/
@RestController
@RequestMapping(value = "/cargos")
public class CargoResources {
	/**
	*Retorna todos os cargos possiveis
	*/
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<TipoUsuario[]> cargos() {
		return ResponseEntity.ok().body(TipoUsuario.values());
	}

}
