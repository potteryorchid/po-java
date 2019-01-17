package com.po.util;

class EnvEntry {

  private String v;
  private String defV;

  /**
   * Define an env element entry
   *
   * @param v : an environmental variable
   * @param defV : default value to an environmental variable
   */
  public EnvEntry(String v, String defV) {
    this.v = v;
    this.defV = defV;
  }

  /**
   * Set an exist environmental variable to a new value
   *
   * @param v : a new value
   */
  public void setV(String v) {
    this.v = v;
  }

  /**
   * Get a value of an environmental variable, match the first one in order { value, default value,
   * null }
   *
   * @return an environmental variable value
   */
  public String getV() {
    if (v == null) {
      if (defV == null) {
        return null;
      } else {
        return this.defV;
      }
    } else {
      return v;
    }
  }
}
