package com.adeilsonl.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adeilsonl.todosimple.models.User;
import com.adeilsonl.todosimple.repositories.TaskRepository;
import com.adeilsonl.todosimple.repositories.UserRepository;

@Service
public class UserService {
    //avisar e instanciar os repositórios
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    // public UserService(UserRepository userRepository, TaskRepository taskRepository) {
    //     this.userRepository = userRepository;
    //     this.taskRepository = taskRepository;
    // }

    public User findById(Long id) {
        //pode receber ou nao o usuario. se nao tiver no banco retornar vazio ao inves de null
        Optional<User> user = this.userRepository.findById(id);
        //so retorna o usuario se nao tiver vazio
        //new Exception para a aplicação
        return user.orElseThrow(() -> new RuntimeException(
            "Usuário não encontrado! id:" + id + ", Tipo: " + User.class.getName()
        ));
    }

    //utilizar para criar ou modificar algo no banco
    //ou faz tudo ou faz nada. salvar os dados em memoria para melhorar o processamento
    @Transactional
    public User create(User obj) {
        //garantir que vai criar um usuario novo
        obj.setId(null);
        obj = this.userRepository.save(obj);
        //ao criar a conta, criar tasks
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir, pois há entidades relacionadas!");
        }
    }

}
