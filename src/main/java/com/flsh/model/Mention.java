package com.flsh.model;

public class Mention {
    // mnt_id 	mnt_intervalle 	mnt_libelle 
	private int mention_id;
	private String mention_intervalle;
	private String mention_libelle;
	
	public int getMentionId() {
		return this.mention_id;
	}
	public void setMentionId(int mention_id) {
		this.mention_id = mention_id;
	}
	public String getMentionIntervalle() {
		return this.mention_intervalle;
	}
	public void setMentionIntervalle(String mention_intervalle) {
		this.mention_intervalle = mention_intervalle;
	}
	public String getMentionLibelle() {
		return this.mention_libelle;
	}
	public void setMentionLibelle(String mention_libelle) {
		this.mention_libelle = mention_libelle;
	}
}
