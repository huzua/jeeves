package com.cherry.jeeves;

import com.cherry.jeeves.domain.shared.*;
import com.cherry.jeeves.domain.turlingRobot.ChatMessage;
import com.cherry.jeeves.service.MessageHandler;
import com.cherry.jeeves.service.WechatHttpService;
import com.cherry.jeeves.utils.Alimama.HttpClientDemo;
import com.cherry.jeeves.utils.Alimama.Item;
import com.cherry.jeeves.utils.Alimama.Template;
import com.cherry.jeeves.utils.MessageUtils;
import com.cherry.jeeves.utils.trulingRobot.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MessageHandlerImpl implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerImpl.class);
    @Autowired
    private WechatHttpService wechatHttpService;
    private boolean robotstart=false;

    //private String Template="复制淘口令后打开淘宝，点击进入弹窗，下单成功即可领取奖励金。";

    private Map map = null;

    @Override
    public void onReceivingChatRoomTextMessage(Message message) {
        logger.info("onReceivingChatRoomTextMessage");
        logger.info("from chatroom: " + message.getFromUserName());
        logger.info("from person: " + MessageUtils.getSenderOfChatRoomTextMessage(message.getContent()));
        logger.info("to: " + message.getToUserName());
        logger.info("content:" + MessageUtils.getChatRoomTextMessageContent(message.getContent()));
    }

    @Override
    public void onReceivingChatRoomImageMessage(Message message, String thumbImageUrl, String fullImageUrl) {
        logger.info("onReceivingChatRoomImageMessage");
        logger.info("thumbImageUrl:" + thumbImageUrl);
        logger.info("fullImageUrl:" + fullImageUrl);
    }

    @Override
    public void onReceivingPrivateTextMessage(Message message) throws IOException {
        logger.info("onReceivingPrivateTextMessage");
        logger.info("from: " + message.getFromUserName());
        logger.info("to: " + message.getToUserName());
        logger.info("content:" + message.getContent());
//        将原文回复给对方
        replyMessage(message);
    }

    @Override
    public void onReceivingPrivateImageMessage(Message message, String thumbImageUrl, String fullImageUrl) throws IOException {
        logger.info("onReceivingPrivateImageMessage");
        logger.info("thumbImageUrl:" + thumbImageUrl);
        logger.info("fullImageUrl:" + fullImageUrl);
//        将图片保存在本地
        byte[] data = wechatHttpService.downloadImage(thumbImageUrl);
        FileOutputStream fos = new FileOutputStream("thumb.jpg");
        fos.write(data);
        fos.close();
    }

    @Override
    public boolean onReceivingFriendInvitation(RecommendInfo info) {
        logger.info("onReceivingFriendInvitation");
        logger.info("recommendinfo content:" + info.getContent());
//        默认接收所有的邀请
        return true;
    }

    @Override
    public void postAcceptFriendInvitation(Message message) throws IOException {
        logger.info("postAcceptFriendInvitation");
//        将该用户的微信号设置成他的昵称
        String content = StringEscapeUtils.unescapeXml(message.getContent());
        ObjectMapper xmlMapper = new XmlMapper();
        FriendInvitationContent friendInvitationContent = xmlMapper.readValue(content, FriendInvitationContent.class);
        wechatHttpService.setAlias(message.getRecommendInfo().getUserName(), friendInvitationContent.getFromusername());
    }

    @Override
    public void onChatRoomMembersChanged(Contact chatRoom, Set<ChatRoomMember> membersJoined, Set<ChatRoomMember> membersLeft) {
        logger.info("onChatRoomMembersChanged");
        logger.info("chatRoom:" + chatRoom.getUserName());
        if (membersJoined != null && membersJoined.size() > 0) {
            logger.info("membersJoined:" + String.join(",", membersJoined.stream().map(ChatRoomMember::getNickName).collect(Collectors.toList())));
        }
        if (membersLeft != null && membersLeft.size() > 0) {
            logger.info("membersLeft:" + String.join(",", membersLeft.stream().map(ChatRoomMember::getNickName).collect(Collectors.toList())));
        }
    }

    @Override
    public void onNewChatRoomsFound(Set<Contact> chatRooms) {
        logger.info("onNewChatRoomsFound");
        chatRooms.forEach(x -> logger.info(x.getUserName()));
    }

    @Override
    public void onChatRoomsDeleted(Set<Contact> chatRooms) {
        logger.info("onChatRoomsDeleted");
        chatRooms.forEach(x -> logger.info(x.getUserName()));
    }

    @Override
    public void onNewFriendsFound(Set<Contact> contacts) {
        logger.info("onNewFriendsFound");
        contacts.forEach(x -> {
            logger.info(x.getUserName());
            logger.info(x.getNickName());
        });
    }

    @Override
    public void onFriendsDeleted(Set<Contact> contacts) {
        logger.info("onFriendsDeleted");
        contacts.forEach(x -> {
            logger.info(x.getUserName());
            logger.info(x.getNickName());
        });
    }

    @Override
    public void onNewMediaPlatformsFound(Set<Contact> mps) {
        logger.info("onNewMediaPlatformsFound");
    }

    @Override
    public void onMediaPlatformsDeleted(Set<Contact> mps) {
        logger.info("onMediaPlatformsDeleted");
    }

    @Override
    public void onRedPacketReceived(Contact contact) {
        logger.info("onRedPacketReceived");
        if (contact != null) {
            logger.info("the red packet is from " + contact.getNickName());
        }
    }
    //回复信息
    private void replyMessage(Message message) throws IOException {
            if (message.getContent().contains("https://")) {
                if (new Item().getItemDetails(message.getContent())) {
                    map = Item.returnMes();
                    String coup = (String) map.get("couponInfo");
                    Double fee = (Double) map.get("tkCommFee");
                    String feearg = String.format("%.2f", fee);
                    String koulin = (String) map.get("koulin");
                    //针对有佣金但不一定有优惠券的情况
                    String couponkey = null;
                    if (map.get("shortkey") != null) {
                        couponkey = map.get("shortkey").toString();
                    }

                    wechatHttpService.sendText(message.getFromUserName(), Template.getMessage(coup, feearg, koulin, couponkey));
                } else {
                    wechatHttpService.sendText(message.getFromUserName(), "该商品暂无优惠信息，淘宝90%的商品都有优惠哦");
                }
            } else if (message.getContent().equals("月光宝盒开")) {
                robotstart = true;
            } else if (message.getContent().equals("月光宝盒关")) {
                robotstart = false;
            }
            if(robotstart){
                ChatMessage chatMessage = HttpUtils.sendMessage(message.getContent());
                wechatHttpService.sendText(message.getFromUserName(), chatMessage.getMsg());
            }

       // HttpClientDemo.getRedirectInfo();
        //ChatMessage chatMessage = HttpUtils.sendMessage(message.getContent());
       //wechatHttpService.sendText(message.getFromUserName(), chatMessage.getMsg());


    }
}
