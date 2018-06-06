/**
 * GameOverviewPerLocationRecord.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hy.pull.common.util.game.hb;

public class GameOverviewPerLocationRecord  implements java.io.Serializable {
    private java.lang.String locationName;

    private java.lang.String gameTypeName;

    private java.lang.String gameName;

    private int totalPlayers;

    private int totalGames;

    private java.math.BigDecimal stake;

    private java.math.BigDecimal gamePayout;

    private java.math.BigDecimal jackpotPayout;

    private java.math.BigDecimal totalPayout;

    private java.math.BigDecimal gameNett;

    private java.math.BigDecimal totalNett;

    private java.math.BigDecimal expectedRTP;

    private java.math.BigDecimal gameRTP;

    private java.math.BigDecimal totalRTP;

    private java.math.BigDecimal avgWagerPerPlayer;

    private int averagesGamesPerPlayer;

    private java.math.BigDecimal averageBetPerGame;

    private java.lang.String brandLocationId;

    public GameOverviewPerLocationRecord() {
    }

    public GameOverviewPerLocationRecord(
           java.lang.String locationName,
           java.lang.String gameTypeName,
           java.lang.String gameName,
           int totalPlayers,
           int totalGames,
           java.math.BigDecimal stake,
           java.math.BigDecimal gamePayout,
           java.math.BigDecimal jackpotPayout,
           java.math.BigDecimal totalPayout,
           java.math.BigDecimal gameNett,
           java.math.BigDecimal totalNett,
           java.math.BigDecimal expectedRTP,
           java.math.BigDecimal gameRTP,
           java.math.BigDecimal totalRTP,
           java.math.BigDecimal avgWagerPerPlayer,
           int averagesGamesPerPlayer,
           java.math.BigDecimal averageBetPerGame,
           java.lang.String brandLocationId) {
           this.locationName = locationName;
           this.gameTypeName = gameTypeName;
           this.gameName = gameName;
           this.totalPlayers = totalPlayers;
           this.totalGames = totalGames;
           this.stake = stake;
           this.gamePayout = gamePayout;
           this.jackpotPayout = jackpotPayout;
           this.totalPayout = totalPayout;
           this.gameNett = gameNett;
           this.totalNett = totalNett;
           this.expectedRTP = expectedRTP;
           this.gameRTP = gameRTP;
           this.totalRTP = totalRTP;
           this.avgWagerPerPlayer = avgWagerPerPlayer;
           this.averagesGamesPerPlayer = averagesGamesPerPlayer;
           this.averageBetPerGame = averageBetPerGame;
           this.brandLocationId = brandLocationId;
    }


    /**
     * Gets the locationName value for this GameOverviewPerLocationRecord.
     * 
     * @return locationName
     */
    public java.lang.String getLocationName() {
        return locationName;
    }


    /**
     * Sets the locationName value for this GameOverviewPerLocationRecord.
     * 
     * @param locationName
     */
    public void setLocationName(java.lang.String locationName) {
        this.locationName = locationName;
    }


    /**
     * Gets the gameTypeName value for this GameOverviewPerLocationRecord.
     * 
     * @return gameTypeName
     */
    public java.lang.String getGameTypeName() {
        return gameTypeName;
    }


    /**
     * Sets the gameTypeName value for this GameOverviewPerLocationRecord.
     * 
     * @param gameTypeName
     */
    public void setGameTypeName(java.lang.String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }


    /**
     * Gets the gameName value for this GameOverviewPerLocationRecord.
     * 
     * @return gameName
     */
    public java.lang.String getGameName() {
        return gameName;
    }


    /**
     * Sets the gameName value for this GameOverviewPerLocationRecord.
     * 
     * @param gameName
     */
    public void setGameName(java.lang.String gameName) {
        this.gameName = gameName;
    }


    /**
     * Gets the totalPlayers value for this GameOverviewPerLocationRecord.
     * 
     * @return totalPlayers
     */
    public int getTotalPlayers() {
        return totalPlayers;
    }


    /**
     * Sets the totalPlayers value for this GameOverviewPerLocationRecord.
     * 
     * @param totalPlayers
     */
    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }


    /**
     * Gets the totalGames value for this GameOverviewPerLocationRecord.
     * 
     * @return totalGames
     */
    public int getTotalGames() {
        return totalGames;
    }


    /**
     * Sets the totalGames value for this GameOverviewPerLocationRecord.
     * 
     * @param totalGames
     */
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }


    /**
     * Gets the stake value for this GameOverviewPerLocationRecord.
     * 
     * @return stake
     */
    public java.math.BigDecimal getStake() {
        return stake;
    }


    /**
     * Sets the stake value for this GameOverviewPerLocationRecord.
     * 
     * @param stake
     */
    public void setStake(java.math.BigDecimal stake) {
        this.stake = stake;
    }


    /**
     * Gets the gamePayout value for this GameOverviewPerLocationRecord.
     * 
     * @return gamePayout
     */
    public java.math.BigDecimal getGamePayout() {
        return gamePayout;
    }


    /**
     * Sets the gamePayout value for this GameOverviewPerLocationRecord.
     * 
     * @param gamePayout
     */
    public void setGamePayout(java.math.BigDecimal gamePayout) {
        this.gamePayout = gamePayout;
    }


    /**
     * Gets the jackpotPayout value for this GameOverviewPerLocationRecord.
     * 
     * @return jackpotPayout
     */
    public java.math.BigDecimal getJackpotPayout() {
        return jackpotPayout;
    }


    /**
     * Sets the jackpotPayout value for this GameOverviewPerLocationRecord.
     * 
     * @param jackpotPayout
     */
    public void setJackpotPayout(java.math.BigDecimal jackpotPayout) {
        this.jackpotPayout = jackpotPayout;
    }


    /**
     * Gets the totalPayout value for this GameOverviewPerLocationRecord.
     * 
     * @return totalPayout
     */
    public java.math.BigDecimal getTotalPayout() {
        return totalPayout;
    }


    /**
     * Sets the totalPayout value for this GameOverviewPerLocationRecord.
     * 
     * @param totalPayout
     */
    public void setTotalPayout(java.math.BigDecimal totalPayout) {
        this.totalPayout = totalPayout;
    }


    /**
     * Gets the gameNett value for this GameOverviewPerLocationRecord.
     * 
     * @return gameNett
     */
    public java.math.BigDecimal getGameNett() {
        return gameNett;
    }


    /**
     * Sets the gameNett value for this GameOverviewPerLocationRecord.
     * 
     * @param gameNett
     */
    public void setGameNett(java.math.BigDecimal gameNett) {
        this.gameNett = gameNett;
    }


    /**
     * Gets the totalNett value for this GameOverviewPerLocationRecord.
     * 
     * @return totalNett
     */
    public java.math.BigDecimal getTotalNett() {
        return totalNett;
    }


    /**
     * Sets the totalNett value for this GameOverviewPerLocationRecord.
     * 
     * @param totalNett
     */
    public void setTotalNett(java.math.BigDecimal totalNett) {
        this.totalNett = totalNett;
    }


    /**
     * Gets the expectedRTP value for this GameOverviewPerLocationRecord.
     * 
     * @return expectedRTP
     */
    public java.math.BigDecimal getExpectedRTP() {
        return expectedRTP;
    }


    /**
     * Sets the expectedRTP value for this GameOverviewPerLocationRecord.
     * 
     * @param expectedRTP
     */
    public void setExpectedRTP(java.math.BigDecimal expectedRTP) {
        this.expectedRTP = expectedRTP;
    }


    /**
     * Gets the gameRTP value for this GameOverviewPerLocationRecord.
     * 
     * @return gameRTP
     */
    public java.math.BigDecimal getGameRTP() {
        return gameRTP;
    }


    /**
     * Sets the gameRTP value for this GameOverviewPerLocationRecord.
     * 
     * @param gameRTP
     */
    public void setGameRTP(java.math.BigDecimal gameRTP) {
        this.gameRTP = gameRTP;
    }


    /**
     * Gets the totalRTP value for this GameOverviewPerLocationRecord.
     * 
     * @return totalRTP
     */
    public java.math.BigDecimal getTotalRTP() {
        return totalRTP;
    }


    /**
     * Sets the totalRTP value for this GameOverviewPerLocationRecord.
     * 
     * @param totalRTP
     */
    public void setTotalRTP(java.math.BigDecimal totalRTP) {
        this.totalRTP = totalRTP;
    }


    /**
     * Gets the avgWagerPerPlayer value for this GameOverviewPerLocationRecord.
     * 
     * @return avgWagerPerPlayer
     */
    public java.math.BigDecimal getAvgWagerPerPlayer() {
        return avgWagerPerPlayer;
    }


    /**
     * Sets the avgWagerPerPlayer value for this GameOverviewPerLocationRecord.
     * 
     * @param avgWagerPerPlayer
     */
    public void setAvgWagerPerPlayer(java.math.BigDecimal avgWagerPerPlayer) {
        this.avgWagerPerPlayer = avgWagerPerPlayer;
    }


    /**
     * Gets the averagesGamesPerPlayer value for this GameOverviewPerLocationRecord.
     * 
     * @return averagesGamesPerPlayer
     */
    public int getAveragesGamesPerPlayer() {
        return averagesGamesPerPlayer;
    }


    /**
     * Sets the averagesGamesPerPlayer value for this GameOverviewPerLocationRecord.
     * 
     * @param averagesGamesPerPlayer
     */
    public void setAveragesGamesPerPlayer(int averagesGamesPerPlayer) {
        this.averagesGamesPerPlayer = averagesGamesPerPlayer;
    }


    /**
     * Gets the averageBetPerGame value for this GameOverviewPerLocationRecord.
     * 
     * @return averageBetPerGame
     */
    public java.math.BigDecimal getAverageBetPerGame() {
        return averageBetPerGame;
    }


    /**
     * Sets the averageBetPerGame value for this GameOverviewPerLocationRecord.
     * 
     * @param averageBetPerGame
     */
    public void setAverageBetPerGame(java.math.BigDecimal averageBetPerGame) {
        this.averageBetPerGame = averageBetPerGame;
    }


    /**
     * Gets the brandLocationId value for this GameOverviewPerLocationRecord.
     * 
     * @return brandLocationId
     */
    public java.lang.String getBrandLocationId() {
        return brandLocationId;
    }


    /**
     * Sets the brandLocationId value for this GameOverviewPerLocationRecord.
     * 
     * @param brandLocationId
     */
    public void setBrandLocationId(java.lang.String brandLocationId) {
        this.brandLocationId = brandLocationId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameOverviewPerLocationRecord)) return false;
        GameOverviewPerLocationRecord other = (GameOverviewPerLocationRecord) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.locationName==null && other.getLocationName()==null) || 
             (this.locationName!=null &&
              this.locationName.equals(other.getLocationName()))) &&
            ((this.gameTypeName==null && other.getGameTypeName()==null) || 
             (this.gameTypeName!=null &&
              this.gameTypeName.equals(other.getGameTypeName()))) &&
            ((this.gameName==null && other.getGameName()==null) || 
             (this.gameName!=null &&
              this.gameName.equals(other.getGameName()))) &&
            this.totalPlayers == other.getTotalPlayers() &&
            this.totalGames == other.getTotalGames() &&
            ((this.stake==null && other.getStake()==null) || 
             (this.stake!=null &&
              this.stake.equals(other.getStake()))) &&
            ((this.gamePayout==null && other.getGamePayout()==null) || 
             (this.gamePayout!=null &&
              this.gamePayout.equals(other.getGamePayout()))) &&
            ((this.jackpotPayout==null && other.getJackpotPayout()==null) || 
             (this.jackpotPayout!=null &&
              this.jackpotPayout.equals(other.getJackpotPayout()))) &&
            ((this.totalPayout==null && other.getTotalPayout()==null) || 
             (this.totalPayout!=null &&
              this.totalPayout.equals(other.getTotalPayout()))) &&
            ((this.gameNett==null && other.getGameNett()==null) || 
             (this.gameNett!=null &&
              this.gameNett.equals(other.getGameNett()))) &&
            ((this.totalNett==null && other.getTotalNett()==null) || 
             (this.totalNett!=null &&
              this.totalNett.equals(other.getTotalNett()))) &&
            ((this.expectedRTP==null && other.getExpectedRTP()==null) || 
             (this.expectedRTP!=null &&
              this.expectedRTP.equals(other.getExpectedRTP()))) &&
            ((this.gameRTP==null && other.getGameRTP()==null) || 
             (this.gameRTP!=null &&
              this.gameRTP.equals(other.getGameRTP()))) &&
            ((this.totalRTP==null && other.getTotalRTP()==null) || 
             (this.totalRTP!=null &&
              this.totalRTP.equals(other.getTotalRTP()))) &&
            ((this.avgWagerPerPlayer==null && other.getAvgWagerPerPlayer()==null) || 
             (this.avgWagerPerPlayer!=null &&
              this.avgWagerPerPlayer.equals(other.getAvgWagerPerPlayer()))) &&
            this.averagesGamesPerPlayer == other.getAveragesGamesPerPlayer() &&
            ((this.averageBetPerGame==null && other.getAverageBetPerGame()==null) || 
             (this.averageBetPerGame!=null &&
              this.averageBetPerGame.equals(other.getAverageBetPerGame()))) &&
            ((this.brandLocationId==null && other.getBrandLocationId()==null) || 
             (this.brandLocationId!=null &&
              this.brandLocationId.equals(other.getBrandLocationId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLocationName() != null) {
            _hashCode += getLocationName().hashCode();
        }
        if (getGameTypeName() != null) {
            _hashCode += getGameTypeName().hashCode();
        }
        if (getGameName() != null) {
            _hashCode += getGameName().hashCode();
        }
        _hashCode += getTotalPlayers();
        _hashCode += getTotalGames();
        if (getStake() != null) {
            _hashCode += getStake().hashCode();
        }
        if (getGamePayout() != null) {
            _hashCode += getGamePayout().hashCode();
        }
        if (getJackpotPayout() != null) {
            _hashCode += getJackpotPayout().hashCode();
        }
        if (getTotalPayout() != null) {
            _hashCode += getTotalPayout().hashCode();
        }
        if (getGameNett() != null) {
            _hashCode += getGameNett().hashCode();
        }
        if (getTotalNett() != null) {
            _hashCode += getTotalNett().hashCode();
        }
        if (getExpectedRTP() != null) {
            _hashCode += getExpectedRTP().hashCode();
        }
        if (getGameRTP() != null) {
            _hashCode += getGameRTP().hashCode();
        }
        if (getTotalRTP() != null) {
            _hashCode += getTotalRTP().hashCode();
        }
        if (getAvgWagerPerPlayer() != null) {
            _hashCode += getAvgWagerPerPlayer().hashCode();
        }
        _hashCode += getAveragesGamesPerPlayer();
        if (getAverageBetPerGame() != null) {
            _hashCode += getAverageBetPerGame().hashCode();
        }
        if (getBrandLocationId() != null) {
            _hashCode += getBrandLocationId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GameOverviewPerLocationRecord.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameOverviewPerLocationRecord"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "LocationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPlayers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalPlayers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalGames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalGames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stake");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "Stake"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gamePayout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GamePayout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jackpotPayout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "JackpotPayout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPayout");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalPayout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameNett");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameNett"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalNett");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalNett"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expectedRTP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "ExpectedRTP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameRTP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "GameRTP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRTP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "TotalRTP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avgWagerPerPlayer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "AvgWagerPerPlayer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("averagesGamesPerPlayer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "AveragesGamesPerPlayer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("averageBetPerGame");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "AverageBetPerGame"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandLocationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oxypite.com/", "BrandLocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
