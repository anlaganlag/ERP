package com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel;

public class ExcelProductInfo {
    private String sku = "";
    private String asin = "";
    private String title = "";
    private String fnsku = "";
    private String externalId = "";
    private String whoWillPrep = "None Required";
    private String prepType = "--";
    private String whoWillLabel = "Merchant";
    private int expectedQTY;
    private int boxedQTY;
    private String nullValue = "";


    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public static class ExcelProductInfoBuilder {
        @SuppressWarnings("all")
        private String sku;
        @SuppressWarnings("all")
        private String asin;
        @SuppressWarnings("all")
        private String title;
        @SuppressWarnings("all")
        private String fnsku;
        @SuppressWarnings("all")
        private String externalId;
        @SuppressWarnings("all")
        private String whoWillPrep;
        @SuppressWarnings("all")
        private String prepType;
        @SuppressWarnings("all")
        private String whoWillLabel;
        @SuppressWarnings("all")
        private int expectedQTY;
        @SuppressWarnings("all")
        private int boxedQTY;
        @SuppressWarnings("all")
        private String nullValue;

        @SuppressWarnings("all")
        ExcelProductInfoBuilder() {
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder sku(final String sku) {
            this.sku = sku;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder asin(final String asin) {
            this.asin = asin;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder title(final String title) {
            this.title = title;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder fnsku(final String fnsku) {
            this.fnsku = fnsku;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder externalId(final String externalId) {
            this.externalId = externalId;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder whoWillPrep(final String whoWillPrep) {
            this.whoWillPrep = whoWillPrep;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder prepType(final String prepType) {
            this.prepType = prepType;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder whoWillLabel(final String whoWillLabel) {
            this.whoWillLabel = whoWillLabel;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder expectedQTY(final int expectedQTY) {
            this.expectedQTY = expectedQTY;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder boxedQTY(final int boxedQTY) {
            this.boxedQTY = boxedQTY;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfoBuilder nullValue(final String nullValue) {
            this.nullValue = nullValue;
            return this;
        }

        @SuppressWarnings("all")
        public ExcelProductInfo build() {
            return new ExcelProductInfo(this.sku, this.asin, this.title, this.fnsku, this.externalId, this.whoWillPrep, this.prepType, this.whoWillLabel, this.expectedQTY, this.boxedQTY, this.nullValue);
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "ExcelProductInfo.ExcelProductInfoBuilder(sku=" + this.sku + ", asin=" + this.asin + ", title=" + this.title + ", fnsku=" + this.fnsku + ", externalId=" + this.externalId + ", whoWillPrep=" + this.whoWillPrep + ", prepType=" + this.prepType + ", whoWillLabel=" + this.whoWillLabel + ", expectedQTY=" + this.expectedQTY + ", boxedQTY=" + this.boxedQTY + ", nullValue=" + this.nullValue + ")";
        }
    }

    @SuppressWarnings("all")
    public static ExcelProductInfoBuilder builder() {
        return new ExcelProductInfoBuilder();
    }

    @SuppressWarnings("all")
    public String getSku() {
        return this.sku;
    }

    @SuppressWarnings("all")
    public String getAsin() {
        return this.asin;
    }

    @SuppressWarnings("all")
    public String getTitle() {
        return this.title;
    }

    @SuppressWarnings("all")
    public String getFnsku() {
        return this.fnsku;
    }

    @SuppressWarnings("all")
    public String getExternalId() {
        return this.externalId;
    }

    @SuppressWarnings("all")
    public String getWhoWillPrep() {
        return this.whoWillPrep;
    }

    @SuppressWarnings("all")
    public String getPrepType() {
        return this.prepType;
    }

    @SuppressWarnings("all")
    public String getWhoWillLabel() {
        return this.whoWillLabel;
    }

    @SuppressWarnings("all")
    public int getExpectedQTY() {
        return this.expectedQTY;
    }

    @SuppressWarnings("all")
    public int getBoxedQTY() {
        return this.boxedQTY;
    }

    @SuppressWarnings("all")
    public String getNullValue() {
        return this.nullValue;
    }

    @SuppressWarnings("all")
    public void setSku(final String sku) {
        this.sku = sku;
    }

    @SuppressWarnings("all")
    public void setAsin(final String asin) {
        this.asin = asin;
    }

    @SuppressWarnings("all")
    public void setTitle(final String title) {
        this.title = title;
    }

    @SuppressWarnings("all")
    public void setFnsku(final String fnsku) {
        this.fnsku = fnsku;
    }

    @SuppressWarnings("all")
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    @SuppressWarnings("all")
    public void setWhoWillPrep(final String whoWillPrep) {
        this.whoWillPrep = whoWillPrep;
    }

    @SuppressWarnings("all")
    public void setPrepType(final String prepType) {
        this.prepType = prepType;
    }

    @SuppressWarnings("all")
    public void setWhoWillLabel(final String whoWillLabel) {
        this.whoWillLabel = whoWillLabel;
    }

    @SuppressWarnings("all")
    public void setExpectedQTY(final int expectedQTY) {
        this.expectedQTY = expectedQTY;
    }

    @SuppressWarnings("all")
    public void setBoxedQTY(final int boxedQTY) {
        this.boxedQTY = boxedQTY;
    }

    @SuppressWarnings("all")
    public void setNullValue(final String nullValue) {
        this.nullValue = nullValue;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ExcelProductInfo)) return false;
        final ExcelProductInfo other = (ExcelProductInfo) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getExpectedQTY() != other.getExpectedQTY()) return false;
        if (this.getBoxedQTY() != other.getBoxedQTY()) return false;
        final Object this$sku = this.getSku();
        final Object other$sku = other.getSku();
        if (this$sku == null ? other$sku != null : !this$sku.equals(other$sku)) return false;
        final Object this$asin = this.getAsin();
        final Object other$asin = other.getAsin();
        if (this$asin == null ? other$asin != null : !this$asin.equals(other$asin)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$fnsku = this.getFnsku();
        final Object other$fnsku = other.getFnsku();
        if (this$fnsku == null ? other$fnsku != null : !this$fnsku.equals(other$fnsku)) return false;
        final Object this$externalId = this.getExternalId();
        final Object other$externalId = other.getExternalId();
        if (this$externalId == null ? other$externalId != null : !this$externalId.equals(other$externalId)) return false;
        final Object this$whoWillPrep = this.getWhoWillPrep();
        final Object other$whoWillPrep = other.getWhoWillPrep();
        if (this$whoWillPrep == null ? other$whoWillPrep != null : !this$whoWillPrep.equals(other$whoWillPrep)) return false;
        final Object this$prepType = this.getPrepType();
        final Object other$prepType = other.getPrepType();
        if (this$prepType == null ? other$prepType != null : !this$prepType.equals(other$prepType)) return false;
        final Object this$whoWillLabel = this.getWhoWillLabel();
        final Object other$whoWillLabel = other.getWhoWillLabel();
        if (this$whoWillLabel == null ? other$whoWillLabel != null : !this$whoWillLabel.equals(other$whoWillLabel)) return false;
        final Object this$nullValue = this.getNullValue();
        final Object other$nullValue = other.getNullValue();
        if (this$nullValue == null ? other$nullValue != null : !this$nullValue.equals(other$nullValue)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof ExcelProductInfo;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getExpectedQTY();
        result = result * PRIME + this.getBoxedQTY();
        final Object $sku = this.getSku();
        result = result * PRIME + ($sku == null ? 43 : $sku.hashCode());
        final Object $asin = this.getAsin();
        result = result * PRIME + ($asin == null ? 43 : $asin.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $fnsku = this.getFnsku();
        result = result * PRIME + ($fnsku == null ? 43 : $fnsku.hashCode());
        final Object $externalId = this.getExternalId();
        result = result * PRIME + ($externalId == null ? 43 : $externalId.hashCode());
        final Object $whoWillPrep = this.getWhoWillPrep();
        result = result * PRIME + ($whoWillPrep == null ? 43 : $whoWillPrep.hashCode());
        final Object $prepType = this.getPrepType();
        result = result * PRIME + ($prepType == null ? 43 : $prepType.hashCode());
        final Object $whoWillLabel = this.getWhoWillLabel();
        result = result * PRIME + ($whoWillLabel == null ? 43 : $whoWillLabel.hashCode());
        final Object $nullValue = this.getNullValue();
        result = result * PRIME + ($nullValue == null ? 43 : $nullValue.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "ExcelProductInfo(sku=" + this.getSku() + ", asin=" + this.getAsin() + ", title=" + this.getTitle() + ", fnsku=" + this.getFnsku() + ", externalId=" + this.getExternalId() + ", whoWillPrep=" + this.getWhoWillPrep() + ", prepType=" + this.getPrepType() + ", whoWillLabel=" + this.getWhoWillLabel() + ", expectedQTY=" + this.getExpectedQTY() + ", boxedQTY=" + this.getBoxedQTY() + ", nullValue=" + this.getNullValue() + ")";
    }

    @SuppressWarnings("all")
    public ExcelProductInfo(final String sku, final String asin, final String title, final String fnsku, final String externalId, final String whoWillPrep, final String prepType, final String whoWillLabel, final int expectedQTY, final int boxedQTY, final String nullValue) {
        this.sku = sku;
        this.asin = asin;
        this.title = title;
        this.fnsku = fnsku;
        this.externalId = externalId;
        this.whoWillPrep = whoWillPrep;
        this.prepType = prepType;
        this.whoWillLabel = whoWillLabel;
        this.expectedQTY = expectedQTY;
        this.boxedQTY = boxedQTY;
        this.nullValue = nullValue;
    }

    @SuppressWarnings("all")
    public ExcelProductInfo() {
    }
    //</editor-fold>
}
