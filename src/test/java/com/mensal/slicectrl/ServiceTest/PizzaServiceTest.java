package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.Pizzas;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.repository.PizzaRepository;
import com.mensal.slicectrl.service.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PizzaService pizzaService;

    Pizzas pizzas = new Pizzas();

    @BeforeEach
    void setup(){
        pizzas.setTamanho(Tamanho.P);
        pizzas.setPreco(92.8);
        when(pizzaRepository.findByTamanho(Tamanho.P)).thenReturn(Collections.singletonList(pizzas));
        when(modelMapper.map(pizzas, PizzasDTO.class)).thenReturn(new PizzasDTO());
    }

    @Test
    public void testFindByTamanho() {
        Tamanho tamanho = Tamanho.P;
        List<PizzasDTO> result = pizzaService.findByTamanho(tamanho);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pizzaRepository, times(1)).findByTamanho(tamanho);
    }


}
