package com.microseluser.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microseluser.entities.Role;
import com.microseluser.exceptions.EntityNotFoundException;
import com.microseluser.service.IRoleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/users")
@Validated
public class RoleRestController {
	
	@Autowired
	private IRoleService roleService;
	
	/**
	 * Ce endpoint permet à un membre du bureau d'obtenir la liste de tous les
	 * roles Seul un membre du bureau peut consulter cette liste
	 * 
	 * @return
	 */
	@ApiOperation(value = "Affichage de tous les roles par un membre du bureau", response = Role.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"), })
	@GetMapping("/admin/roles")
	public ResponseEntity<List<Role>> showAllRoles() {
		return new ResponseEntity<List<Role>>(roleService.showAllRoles(), HttpStatus.OK);
	}
	
	
	/**
	 * Ce endpoint permet à un utilisateur d'obtenir la liste de tous les
	 * roles liés à son compte personnel. Un autre utilisateur non ADMIN ne peut pas consulter cette liste
	 * 
	 * @return
	 * @throws EntityNotFoundException 
	 */
	@ApiOperation(value = "Affichage de tous les roles par un membre du bureau", response = Role.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"), })
	@GetMapping("/user/roles/{id}")
	public ResponseEntity<List<Role>> showAllRolesByUserId(@PathVariable("id") String userId) throws EntityNotFoundException {
		return new ResponseEntity<List<Role>>(roleService.showAllRolesByUserId(userId), HttpStatus.OK);
	}

}
