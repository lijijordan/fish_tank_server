package com.fish.service;

import java.util.List;

import com.fish.entity.PH;

public interface PHService {
	
	PH getCurrentPH();
	
	List<PH> getPHs(int size);

}
