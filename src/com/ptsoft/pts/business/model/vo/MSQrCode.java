package com.ptsoft.pts.business.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.ptsoft.common.base.BaseEntity;

public class MSQrCode extends BaseEntity
{
	private static final long serialVersionUID = 252290000279745259L;
	
	private List<Integer> qrcodes = new ArrayList<Integer>();

	public MSQrCode(List<Integer> qrcodes) {
		super();
		this.qrcodes = qrcodes;
	}

	public List<Integer> getQrcodes() {
		return qrcodes;
	}

	public void setQrcodes(List<Integer> qrcodes) {
		this.qrcodes = qrcodes;
	} 
}
