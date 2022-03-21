package wood.mike.design.creational.builder;

import java.time.LocalDate;
import java.util.List;

public class Builder {
    public static void main(String[] args) {
        ItsoMessage itsoMessage = new ItsoMessage.MessageBuilder(1, LocalDate.now())
                .originator("63359700478836")
                .recipient("63359700346736")
                .dataFrames( List.of("DF1", "DF2") )
                .build();
    }
}

class ItsoMessage {
    Integer msgClass;
    LocalDate msgDate;
    String originator;
    String recipient;
    List<String> dataFrames;

    public ItsoMessage( MessageBuilder builder ) {
        msgClass = builder.msgClass;
        msgDate = builder.msgDate;
        originator = builder.originator;
        recipient = builder.recipient;
        dataFrames = builder.dataFrames;
    }

    public Integer getMsgClass() {
        return msgClass;
    }

    public void setMsgClass(Integer msgClass) {
        this.msgClass = msgClass;
    }

    public LocalDate getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(LocalDate msgDate) {
        this.msgDate = msgDate;
    }

    public List<String> getDataFrames() {
        return dataFrames;
    }

    public void setDataFrames(List<String> dataFrames) {
        this.dataFrames = dataFrames;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public static class MessageBuilder {
        Integer msgClass;
        LocalDate msgDate;
        String originator;
        String recipient;
        List<String> dataFrames;

        public MessageBuilder( final Integer msgClass, final LocalDate msgDate ) {
            this.msgClass = msgClass;
            this.msgDate = msgDate;
        }

        public MessageBuilder originator( final String originator ) {
            this.originator = originator;
            return this;
        }

        public MessageBuilder recipient( final String recipient ) {
            this.recipient = recipient;
            return this;
        }

        public MessageBuilder dataFrames( final List<String> dataFrames ) {
            this.dataFrames = dataFrames;
            return this;
        }

        public ItsoMessage build() {
            return new ItsoMessage(this);
        }
    }
}
