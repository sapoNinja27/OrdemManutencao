package main.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.domain.enums.TipoUsuario;

@RestController
@RequestMapping(value = "/cargos")
public class CargoResources {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<TipoUsuario[]> cargos() {
//		String obj[] = new String[TipoUsuario.totalTipos()];
//		for (int i = 0; i < obj.length; i++) {
//			obj[i] = TipoUsuario.toString(TipoUsuario.toEnum(i + 1));
//		}
		return ResponseEntity.ok().body(TipoUsuario.values());
	}

}
