#!/usr/bin/env python
#coding:utf-8
import PIL.Image as Image
import PIL.ImageDraw as ImageDraw
import PIL.ImageFont as ImageFont
import requests
import sys
import os


class postMaker(object):
	def __init__(self, backImg, font):
		self.backImg = backImg
		self.font = font
		self.post = None

	def create(self, userIcon, userName, qrImg, textColor, userId):
		"""
		@userIcon: 用户头像URL
		@userName： 用户昵称
		@qrImg: 用户二维码URL
		@textColor: 文字颜色，{R，G，B}
		"""
		try:
			backImg = Image.open(self.backImg)
			# userIcon = Image.open(userIcon)
			font = ImageFont.truetype(self.font, 16)


			# userIcon.thumbnail((88,88))
			# backImg.paste(userIcon, (316,242))

			draw = ImageDraw.Draw(backImg)
			draw.ink = textColor.get('R',0) + textColor.get('G',0) * 256 + textColor.get('B',0)*256*256
			textWidth,textHeight = font.getsize(userName)
			draw.text([220-textWidth/2, 340], userName, font=font)

			qrImg = Image.open(qrImg)
			qrImg.thumbnail((160,160))
			backImg.paste(qrImg,(140,180))

			self.post = backImg
			backImg.save("images/" + userId + ".jpg", "jpeg")
		except Exception as e:
			print(repr(e))

def main(userId):
	# 得到二维码
	qrUrl = "http://api.k780.com:88/?app=qr.get&data=http://www.yitaonet.cn/static/login.html?parent_id=" + userId + "&level=L&size=8";
	# print(qrUrl)
	r = requests.get(qrUrl);
	os.chdir("/usr/local/tomcat8/apache-tomcat-8.5.32/webapps/furuida-app/static/images/qr/");
	qrImg = "qr" + userId + ".jpg"
	with open(qrImg, "wb") as f:
		f.write(r.content)
	# 增加背景图片
	backImg = "backgroud.jpg"
	font = "msyhl.ttc"
	userIcon = ''
	pMaker = postMaker(backImg=backImg, font=font)
	pMaker.create(
		userIcon=userIcon,
		userId = userId,
		userName="邀请码: " + userId,
		qrImg=qrImg,
		textColor={'R': 0, 'G': 0, 'B': 0})

if __name__=='__main__':
	userId = sys.argv[1]
	main(userId)


