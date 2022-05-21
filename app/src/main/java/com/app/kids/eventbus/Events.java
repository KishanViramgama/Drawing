package com.app.kids.eventbus;

public class Events {

    // Event used to send message from color data notify.
    public static class ColorNotify {
        private int color;

        public ColorNotify(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    // Event used to send message from color data notify.
    public static class DeleteNotify {
        private int position;

        public DeleteNotify(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
