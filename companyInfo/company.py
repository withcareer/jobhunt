from time import sleep
from selenium import webdriver
from io import BytesIO
from bs4 import BeautifulSoup
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import json
import os

chrome_options = webdriver.ChromeOptions()
chrome_options.add_argument('--headless')               # headless
chrome_options.add_argument('--no-sandbox')
chrome_options.add_argument('--disable-dev-shm-usage')
chrome_options.add_argument('--disable-gpu')
chrome_options.add_argument('--window-size=1920x1080')

driver = webdriver.Chrome('chromedriver', chrome_options=chrome_options)
driver.implicitly_wait(3)
driver2 = webdriver.Chrome('chromedriver', chrome_options=chrome_options)
driver.implicitly_wait(3)
driver.get('https://www.jobkorea.co.kr/starter/calendar')

parsing_data = {}
BASE_DIR = os.path.dirname(os.path.abspath(__file__))

def processing(data):
    try:
        data.click()
        sleep(2)
        
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')
        
        companyLink = soup.select('div.co > div.coTit > a.coLink')
        companyState = soup.select('div.co > div.coTit > a.coLink > span > strong:nth-child(1)')
        companyName = soup.select('div.co > div.coTit > a.coLink > span')
        companyContent = soup.select('div.info > div.tit > a.link > span')
        companyPosition = soup.select('div.sDesc > strong')
        companyPlan = soup.select('div.side > span.day')
        inner_link=soup.select('div.info > div.tit > a.link')
        
        for link, name, state, content, position, plan , inner_link in zip(companyLink, companyName, companyState, companyContent, companyPosition, companyPlan , inner_link):
            state = state.get_text().strip()
            link = link.get('href')
            name = name.get_text().strip()[2:]
            content = content.get_text().strip()
            position = position.get_text().strip()
            plan = plan.get_text().strip()
            in_link=inner_link.get('href')
            
            driver2.get('https://www.jobkorea.co.kr'+in_link)
            sleep(2)

            companyimg=driver2.find_element(By.CSS_SELECTOR, "#cologo").get_attribute("src")
            start_end_day=driver2.find_element(By.CSS_SELECTOR,"dl.date").text
            array=start_end_day.split(" ")
            
            start_day=array[1]
            end_day=array[3]
   
            
            
            # endday=driver2.find_element(By.CSS_SELECTOR,"dl.date > dd:nth-child(2)").text


            #if state == "시작" or state == "예상":
            print(state, name, content, position, plan, link ,companyimg,start_day,end_day)
            parsing_data[name] = {
                "state" : state,
                "content" : content,
                "position" : position,
                "plan" : plan,
                "link" : link,
                "img" :companyimg,
                "시작일":start_day,
                "종료일":end_day
                
            }

        driver.find_element(By.CSS_SELECTOR,'button.closeCalLy').click()

    except Exception as e:
        # print(e)
        return None

infolst1 = driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(1) > td.sunday > div > div > div > span.moreNum')
infolst1 = infolst1 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(1) > td > div > div > div > span.moreNum')
infolst1 = infolst1 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(1) > td.saturday > div > div > div > span.moreNum')
infolst2 = driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(2) > td.sunday > div > div > div > span.moreNum')
infolst2 = infolst2 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(2) > td > div > div > div > span.moreNum')
infolst2 = infolst2 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(2) > td.saturday > div > div > div > span.moreNum')
infolst3 = driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(3) > td.sunday > div > div > div > span.moreNum')
infolst3 = infolst3 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(3) > td > div > div > div > span.moreNum')
infolst3 = infolst3 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(3) > td.saturday > div > div > div > span.moreNum')
infolst4 = driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(4) > td.sunday > div > div > div > span.moreNum')
infolst4 = infolst4 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(4) > td > div > div > div > span.moreNum')
infolst4 = infolst4 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(4) > td.saturday > div > div > div > span.moreNum')
infolst5 = driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(5) > td.sunday > div > div > div > span.moreNum')
infolst5 = infolst5 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(5) > td > div > div > div > span.moreNum')
infolst5 = infolst5 + driver.find_elements(By.CSS_SELECTOR,'#container > div.stContainer > div.calContent > div.starNowMonth > table > tbody > tr:nth-child(5) > td.saturday > div > div > div > span.moreNum')

infolst = infolst1 + infolst2 + infolst3 + infolst4 + infolst5

for data in infolst:
    if (processing(data) != None):
        pass
        # print(processing(data))
driver.quit()


with open(os.path.join(BASE_DIR, 'news.json'), 'w+',encoding='utf-8') as json_file:
    json.dump(parsing_data, json_file, ensure_ascii = False, indent='\t')

print("완료!")
