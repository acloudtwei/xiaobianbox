package com.example.one;

import java.util.List;

public class javabean {

    private String counts;
    private List<Lives> lives;
    public String getCounts() { return counts;}
    public void setCounts(String count){ this.counts = count;}
    public List<Lives> getLives() { return lives;}
    public void setLives(List<Lives> index) {this.lives = index;}

    class Lives {

        private String province;
        private String city;
        private String weather;
        private String temperature;

        public String getProvince() {
            return province;
        }
        public void setProvince(String provinces)
        {
            this.province = provinces;
        }
        public String getCity() {
            return city;
        }
        public void setCity(String citys){
            this.city = citys;
        }
        public String getWeather(){
            return weather;
        }
        public void setWeather(String weathers)
        {
            this.weather = weathers;
        }
        public String getTemperature()
        {
            return temperature;
        }
        public void setTemperature(String temperatures)
        {
            this.temperature = temperatures;
        }
    }

}
