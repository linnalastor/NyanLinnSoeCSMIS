package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.ConsumerList;

public interface AdminConsumerListServiceInterface {
	List<ConsumerList> getAllConsumerLists();

	ConsumerList getConsumerList(String id);

}
