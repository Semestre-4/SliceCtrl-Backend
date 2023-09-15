package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.dto.SaboresDTO;
import com.mensal.slicectrl.entity.Ingredientes;
import com.mensal.slicectrl.entity.Sabores;
import com.mensal.slicectrl.repository.IngredienteRepository;
import com.mensal.slicectrl.repository.SaboresRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaboresService {

    private final SaboresRepository saboresRepository;
    private final IngredienteRepository ingredienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SaboresService(SaboresRepository saboresRepository,
                          IngredienteRepository ingredienteRepository,
                          ModelMapper modelMapper) {
        this.saboresRepository = saboresRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.modelMapper = modelMapper;
    }

    private SaboresDTO toSaboresDTO(Sabores sabores) {
        SaboresDTO saboresDTO = modelMapper.map(sabores, SaboresDTO.class);

        List<IngredientesDTO> ingredientesDTOS = new ArrayList<>();
        for (Ingredientes ingredientes : sabores.getIngredientes()) {
            IngredientesDTO ingredientesDTO = modelMapper.map(ingredientes, IngredientesDTO.class);
            ingredientesDTOS.add(ingredientesDTO);
        }

        saboresDTO.setIngredientesDTOS(ingredientesDTOS);

        return saboresDTO;
    }


    private Sabores toSabores (SaboresDTO saboresDTO){
        return modelMapper.map(saboresDTO, Sabores.class);
    }

    public List<SaboresDTO> getAll(){
        return saboresRepository.findAll().stream().map(this::toSaboresDTO).toList();
    }

    public SaboresDTO getByNome(String nomeSabor){
        Sabores sabores = this.saboresRepository.findByNome(nomeSabor);
        return toSaboresDTO(sabores);
    }

    public SaboresDTO getById(Long id){
        Sabores sabores = this.saboresRepository.findById(id).orElse(null);
        return toSaboresDTO(sabores);
    }

    @Transactional
    public Sabores cadastrar(SaboresDTO saboresDTO) {
        String nomeSabor = saboresDTO.getNomeSabor();

        Optional<Sabores> saborExistente = saboresRepository.findByNomeSabor(nomeSabor);
        if (saborExistente.isPresent()) {
            throw new IllegalArgumentException("O sabor com o nome '" + nomeSabor + "' já existe.");
        }

        List<Ingredientes> ingredientes = new ArrayList<>();

        for (IngredientesDTO ingredientesDTO : saboresDTO.getIngredientesDTOS()) {
            Ingredientes ingredientes1 = modelMapper.map(ingredientesDTO, Ingredientes.class);

            Ingredientes ingredientesfiltrado = ingredienteRepository.findByNome(ingredientes1.getNomeIngrediente());

            if (ingredientesfiltrado == null){
                ingredientes.add(ingredientes1);
            }else {
                ingredientes.add(ingredientesfiltrado);
            }
        }

        Sabores sabores = toSabores(saboresDTO);
        sabores.setIngredientes(ingredientes);

        return saboresRepository.save(sabores);
    }


    @Transactional
    public Sabores editar(Long id, SaboresDTO saboresDTO) {
        Optional<Sabores> existingSaboresOptional = saboresRepository.findById(id);

        if (existingSaboresOptional.isEmpty()) {
            throw new IllegalArgumentException("O objeto com o ID fornecido não existe na base de dados");
        }

        if (!id.equals(saboresDTO.getId())) {
            throw new IllegalArgumentException("O ID na URL não corresponde ao ID no corpo da requisição");
        }
        Sabores updatedSabores = toSabores(saboresDTO);
        return saboresRepository.save(updatedSabores);
    }

    @Transactional
    public void deletar(Long id) {
        this.saboresRepository.findById(id).ifPresent(this.saboresRepository::delete);
    }


    }


