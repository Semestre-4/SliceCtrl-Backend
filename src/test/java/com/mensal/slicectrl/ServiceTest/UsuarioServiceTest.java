package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.dto.UsuarioFrontDTO;
import com.mensal.slicectrl.entity.Usuario;
import com.mensal.slicectrl.repository.UsuarioRepository;
import com.mensal.slicectrl.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    private List<Usuario> usuarioList = new ArrayList<>();
    Usuario usuario = new Usuario();
    UsuarioFrontDTO usuarioDTO = new UsuarioFrontDTO("0202938920","ADMIN","123456789");

    UsuarioDTO usuarioDTO1 = new UsuarioDTO("0202938920","ADMIN","123456789");

    Usuario usuario2 = new Usuario();

    @BeforeEach
    void setUp(){
        usuario.setId(1L);
        usuario.setNome("John");
        usuario.setCpf("0202938920");
        usuario.setTelefone("1111");
        usuario.setSalario(BigDecimal.TEN);

        usuario2.setId(2L);
        usuario2.setNome("Alice");
        usuario2.setCpf("723798932323");

        usuarioList.add(usuario);
        usuarioList.add(usuario2);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());
        when(usuarioRepository.findAll()).thenReturn(usuarioList);
        when(usuarioRepository.findByNome("John")).thenReturn(List.of(usuario));
        when(usuarioRepository.findByCpf("0202938920")).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioFrontDTO.class)).thenReturn(usuarioDTO);
        when(modelMapper.map(usuario, UsuarioFrontDTO.class)).thenReturn(usuarioDTO);
        when(modelMapper.map(usuario2, UsuarioFrontDTO.class)).thenReturn(usuarioDTO);
        when(usuarioService.toFunc(usuarioDTO1)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);    }

    @Test
    void testFindById_ValidId() {
        UsuarioFrontDTO result = usuarioService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> usuarioService.findById(2L));
    }

    @Test
    void testGetAll() {
        List<UsuarioFrontDTO> result = usuarioService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindByNome_ValidResult() {
        String validNome = "John";
        when(usuarioRepository.findByNome(validNome)).thenReturn(usuarioList);
        List<UsuarioFrontDTO> result = usuarioService.findByNome(validNome);
        assertFalse(result.isEmpty());
    }

    @Test
    void testCadastrarFuncService(){
        Usuario resposta = usuarioService.createUsuario(usuario);
        assertNotNull(resposta);
        assertEquals(resposta, usuario);
    }

    @Test
    void testUpdateFuncWhenFuncNotFound() {
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.updateUsuario(id, usuario);
        });
    }

    @Test
    void testUpdateFuncWhenIdMismatch() {
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);
        usuario.setId(2L);

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUsuario(id, usuario);
        });
    }



}
