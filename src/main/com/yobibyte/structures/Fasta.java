package com.yobibyte.structures;


public class Fasta implements Comparable<Fasta>, Cloneable {
  public int id;
  public String generalInfo = "";
  public String proteins = "";
  public Integer length = 0; //num of letters in proteins

    @Override
  public int compareTo(Fasta o) {
		if(this.length < o.length) {
			return -1;
		} 
		else if(this.length > o.length) {
			return 1;
		} 
		return 0;
  }

    @Override
  public Fasta clone() {
      Fasta toReturn = new Fasta();
      toReturn.setGeneralInfo(this.getGeneralInfo());
      toReturn.setId(this.getId());
      toReturn.setProteins(this.getProteins());
      toReturn.setLength(this.getLength());
      return toReturn;
  }

	public String getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(String generalInfo) {
		this.generalInfo = generalInfo;
	}

	public String getProteins() {
		return proteins;
	}

	public void setProteins(String proteins) {
		this.proteins = proteins;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
