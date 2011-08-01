package com.petproject.tools.apmt.data.client;

import java.util.List;

import com.petproject.tools.apmt.data.base.BaseRepository;
import com.petproject.tools.apmt.domain.client.ClientModelImpl;

public interface ClientRepository extends BaseRepository<ClientModelImpl, Long> {
	List<ClientModelImpl> findByCode(String code);
}
