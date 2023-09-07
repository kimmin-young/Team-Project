# -*- coding:utf-8 -*-
from cx_Oracle import connect
from http.client import HTTPSConnection
from json import loads

# - 글번호(PK)  num
# - 메뉴명  RCP_NM
# - 조리방법  RCP_WAY2
# - 요리종류  RCP_PAT2
# - 열량  INFO_ENG
# - 재료정보  RCP_PARTS_DTLS
# - 섬네일  ATT_FILE_NO_MAIN
# - 저감조리법  RCP_NA_TIP
# - 만드는법  MANUAL_IMG
# - 만드는법(이미지 링크)  MANUAL_IMG
# - 조회수  views
# 글번호, 조회수를 제외한 나머지 데이터 타입 전부 문자열

# https://openapi.foodsafetykorea.go.kr/api/sample/COOKRCP01/json/1/5
# https://openapi.foodsafetykorea.go.kr/api/07ef6f7c918a47b7b0ff/COOKRCP01/json/1/30

con = connect("jeong/1@localhost:1521/xe")
cur = con.cursor()

hc = HTTPSConnection("openapi.foodsafetykorea.go.kr")
hc.request("GET", "/api/07ef6f7c918a47b7b0ff/COOKRCP01/json/1/235")
resBody = hc.getresponse().read()
# print(resBody.decode())

data = loads(resBody)
for d in data["COOKRCP01"]["row"]:
    d1 = d["RCP_NM"]
    d2 = d["RCP_WAY2"]
    d3 = d["RCP_PAT2"]
    d4 = d["INFO_ENG"]
    d5 = d["RCP_PARTS_DTLS"]
    d6 = d["ATT_FILE_NO_MAIN"]
    d7 = d["RCP_NA_TIP"]
    MANUAL = ''
    MANUAL_IMG = ''
    for i in range(1,21):
        # print(d[f"MANUAL{i:02d}"])
        # print(d[f"MANUAL_IMG{i:02d}"])
        MANUAL = MANUAL + ( d[f"MANUAL{i:02d}"] + '!!' )
        MANUAL_IMG = MANUAL_IMG + ( d[f"MANUAL_IMG{i:02d}"] + '!!' )
        
    sql = f"insert into p_recipe values(p_recipe_seq.nextval, '{d1}', '{d2}', '{d3}', '{d4}', '{d5}', '{d6}', '{d7}', '{MANUAL}', '{MANUAL_IMG}',0)"
    cur.execute(sql)
    if cur.rowcount >= 1:
        con.commit()
    # print(MANUAL)
    # print(MANUAL_IMG)
    # print('---------------')
print('db 성공!')
con.close()







    
    
    
