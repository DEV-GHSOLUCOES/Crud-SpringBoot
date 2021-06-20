package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /* Injeção de dependencia*/
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(name);
    	usuarioRepository.save(usuario);
    	
        return "Spring Boot " + name + "!";
    }
    
    @GetMapping(value = "listatodos") /*mapeia a url*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<List<Usuario>> listaUsuarios() {
    	/*Excuta a consulta no banco de dados*/
    	List<Usuario> usuarios  = usuarioRepository.findAll();
    	
    	/*retorna a lista em json*/
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    } 
    
    @PostMapping(value = "salvar")/*mapeia a url*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { /* Recebe os dados para salvar*/
    	/*salva  o usuario no banco de dados*/
    	Usuario user = usuarioRepository.save(usuario);
		

    	/*retorna o usuario salvo */
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
    
    
    @PutMapping(value = "atualizar")/*mapeia a url*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { /* Recebe os dados para salvar*/
    	
    	/*retorna uma menssagem caso nao seja informado o id do usuario */
    	if (usuario.getId() == null) {
    		return new ResponseEntity<String>("Id não foi Informado! ", HttpStatus.OK);	
		}
    	
    	/*salva  o usuario no banco de dados*/
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
		/*retorna o usuario salvo */
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
    
    @DeleteMapping(value = "delete")/*mapeia a url*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<String> delete(@RequestParam Long idUser) { /* Recebe os dados para deletar*/
    	/*deleta o usuario no banco de dados*/
    	usuarioRepository.deleteById(idUser);
		

    	/*retorna uma mensagem*/
    	return new ResponseEntity<String>("Usuario deletado com Sucesso! ", HttpStatus.OK);
	}
    
    @GetMapping(value = "buscaruserid")/*mapeia a url*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "idUser") Long idUser) { /* Recebe os dados para buscar*/
    	/*busca o usuario no banco de dados por id*/
    	 Usuario usuario = usuarioRepository.findById(idUser).get();
		

    	/*retorna o usuario*/
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
    
    @GetMapping(value = "buscarPorNome")/*mapeia a url*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "nome") String nome) { /* Recebe os dados para buscar*/
    	
    	/*busca o usuario no banco de dados por nome*/
    	List<Usuario> usuarioList = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());
		

    	/*retorna o usuario*/
    	return new ResponseEntity<List<Usuario>>(usuarioList, HttpStatus.OK);
	}
    
    
}
