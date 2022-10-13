package pers.me.ad.serivce.impl;

import org.springframework.stereotype.Service;
import pers.me.ad.dao.CreativeRepository;
import pers.me.ad.entity.Creative;
import pers.me.ad.serivce.ICreativeService;
import pers.me.ad.vo.CreativeRequest;
import pers.me.ad.vo.CreativeResponse;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }


    @Override
    public CreativeResponse createCreative(CreativeRequest request) {
        Creative creative = creativeRepository.save(
                request.convertToEntity()
        );
        return new CreativeResponse(creative.getId(),creative.getName());
    }
}
