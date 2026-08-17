package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.mainstat.MainStatDao;
import com.sjb.wuwaechorank.dao.entity.presetecho.PresetEchoDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dto.EchoDetailDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.EchoSubStatInfo;
import com.sjb.wuwaechorank.entity.MainStat;
import com.sjb.wuwaechorank.entity.PresetEcho;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.service.substat.SubStatService;

//공명자 에코와 관련된 서비스 구현 클래스
@Service
public class ResonatorEchoServiceImpl implements ResonatorEchoService{
    private ResonatorEchoDao resonatorEchoDao;
    private PresetEchoDao presetEchoDao;
    private EchoDao echoDao;
    private MainStatDao mainStatDao;
    private SubStatService subStatService;
    private EchoSubStatInfoDao echoSubStatInfoDao;
    
    public ResonatorEchoServiceImpl(ResonatorEchoDao resonatorEchoDao, SubStatService subStatService, EchoDao echoDao, MainStatDao mainStatDao, PresetEchoDao presetEchoDao, EchoSubStatInfoDao echoSubStatInfoDao){
        this.resonatorEchoDao = resonatorEchoDao;
        this.subStatService = subStatService;
        this.echoDao = echoDao;
        this.mainStatDao = mainStatDao;
        this.presetEchoDao = presetEchoDao;
        this.echoSubStatInfoDao = echoSubStatInfoDao;
    }

    @Override
    public void saveResonatorEchos(int presetId, List<ResonatorEchoInfoDto> resonatorEchoInfos, List<Double> scores) {
        if(resonatorEchoInfos == null || resonatorEchoInfos.size() == 0){
            return;
        }

        if(scores.size() != resonatorEchoInfos.size()){
            throw new IllegalArgumentException("resonatorEchoInfos와 scores의 개수가 일치하지 않습니다.");
        }

        for (int i=0; i < resonatorEchoInfos.size(); i++) {
            ResonatorEchoInfoDto resonatorEchoInfo = resonatorEchoInfos.get(i);
            ResonatorEcho resoantorEcho = ResonatorEcho.builder()
                    .echoId(resonatorEchoInfo.echoId())
                    .mainStatId(resonatorEchoInfo.mainStatId())
                    .score(scores.get(i))
                    .build();
            
            int resonatorEchoId = this.resonatorEchoDao.add(resoantorEcho);

            PresetEcho presetEcho = PresetEcho.builder()
                    .presetId(presetId)
                    .resonatorEchoId(resonatorEchoId)
                    .build();
            this.presetEchoDao.add(presetEcho);
            
            if(resonatorEchoInfo.echoSubStats() != null & resonatorEchoInfo.echoSubStats().size() != 0){
                List<EchoSubStatInfo> echoSubStatInfos = resonatorEchoInfo.echoSubStats().stream()
                        .map(substat->EchoSubStatInfo.builder()
                                .resonatorEchoId(resonatorEchoId)
                                .subStatInfoId(substat.subStatInfoId())
                                .build()).toList();
    
                echoSubStatInfos.stream().forEach(echoSubStatInfo->this.echoSubStatInfoDao.add(echoSubStatInfo));
            }
        }
    }

    @Override
    public EchoDetailDto getEchoDetail(ResonatorEcho resonatorEcho) {
        Echo echo = this.echoDao.get(resonatorEcho.getEchoId());
        MainStat mainstat = this.mainStatDao.get(resonatorEcho.getMainStatId());
        List<SubStatDetailDto> subStatDetailDtos = this.subStatService.getSubStatDetailsByResonatorEchoId(resonatorEcho.getId());
        return EchoDetailDto.builder()
                .echo(echo)
                .mainstat(mainstat)
                .subStatDetailDtos(subStatDetailDtos)
                .build();
    }

    @Override
    public List<ResonatorEcho> getResonatorEchosByPresetId(int presetId) {
        return this.resonatorEchoDao.getAllByPresetId(presetId);
    }

}
