package com.sjb.wuwaechorank.dao.entity.echo;

import java.util.List;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.entity.Echo;

@DaoCoreInterface
public interface EchoDaoCore {
    List<Echo> getAllBySonataEffect(int id);
}
