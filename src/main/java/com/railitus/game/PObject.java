package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

public abstract class PObject extends GameObject {
    protected int color;
    protected Vector velocity;
    protected Vector acceleration;

    protected float mass = 10;
    protected Vector centroDeMasa;

    protected boolean trackeable = false;
    protected int trackColor = 0xffff0000;
    protected int radius;

    public PObject(){}

    public PObject(Vector pos, int color, String tag){
        this.tag = tag;
        this.pos = pos;
        this.color = color;
        this.velocity = Vector.zero();
        this.acceleration = Vector.zero();
    }

    public PObject(Vector pos, int color, String tag, Vector velocity,float mass){
        this.tag = tag;
        this.pos = pos;
        this.color = color;
        this.velocity = velocity;
        this.acceleration = Vector.zero();
        this.mass = mass;

    }
    public Vector getVelocity() {
        return velocity;
    }

    public float getMass() {
        return mass;
    }

    void applyForce(Vector force) {
        Vector f = Vector.copy(force);
        //f.mul(1/mass);
        acceleration.add(f);
    }

    void setTrackeable(boolean flag){
        trackeable = flag;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean testCollision(PObject other){
        double distanceX = (this.pos.x - other.pos.x);
        double distanceY = (this.pos.y - other.pos.y);
        double distanceAbs = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));

        if(distanceAbs < other.radius+this.radius)
            return true;
        else
            return false;
    }

   /* public static void resolveCollision(PObject mover, PObject stat){
        Vector relativeVel = Vector.copy(mover.velocity);
        Vector posDif = Vector.sub(stat.pos,mover.pos);

        double phi = Math.acos(relativeVel.dot(posDif)/(relativeVel.getLength()*posDif.getLength()));

        double r = stat.mass/mover.mass;
        System.out.println("Debug: r = "+r);
        double w = 2*Math.cos(phi)/(1+r);
        System.out.println("Debug: w = "+w);

        double theta = Math.atan(r*w*Math.sin(phi)/(1-(r*w*Math.sin(phi))));

        double u = Math.sqrt(1- r*Math.pow(w,2));
        System.out.println("Debug: u = "+u);

        double finalV1_mod = u*relativeVel.getLength();
        double finalV2_mod = w*relativeVel.getLength();

        Vector finalV1 = Vector.polar(finalV1_mod,theta);
        Vector finalV2 = Vector.polar(finalV2_mod,-phi);

        System.out.println("finalVel1: Modulo = "+finalV1_mod+"; angulo = "+theta/Math.PI+" pi rad");
        System.out.println("finalVel2: Modulo = "+finalV2_mod+"; angulo = "+phi/Math.PI+" pi rad");

        mover.setVel(finalV1);
        stat.setVel(finalV2);

    }*/

    private void setVel(Vector vel) {
        this.velocity = Vector.copy(vel);
    }

    protected static double calculatePhi(PObject mover, PObject stat){
        Vector velocity = Vector.copy(mover.velocity);
        Vector distance = Vector.sub(stat.pos,mover.pos);

        return Math.acos(velocity.dot(distance)/(velocity.getLength()*distance.getLength()));
    }


    @Override
    public abstract void update(GameContainer gc, GameManager gm, float dt);

    @Override
    public abstract void render(GameContainer gc, Renderer renderer);






    Vector rotate(Vector velocity, double angle) {
        return new Vector(velocity.x * Math.cos(angle) - velocity.y * Math.sin(angle),
                velocity.x * Math.sin(angle) + velocity.y * Math.cos(angle));
    }


    public void resolveCollision(PObject mover1, PObject mover2) {
    double xVelocityDiff = mover1.velocity.x - mover2.velocity.x;
    double yVelocityDiff = mover1.velocity.y - mover2.velocity.y;

    double xDist = mover2.pos.x - mover1.pos.x;
    double yDist = mover2.pos.y - mover1.pos.y;

        // Prevent accidental overlap of particles
        if (xVelocityDiff * xDist + yVelocityDiff * yDist >= 0) {

            // Grab angle between the two colliding particles
        double angle = -Math.atan2(mover2.pos.y - mover1.pos.y, mover2.pos.x - mover1.pos.x);

            // Store mass in var for better readability in collision equation
        double m1 = mover1.mass;
        double m2 = mover2.mass;

            // Velocity before equation
        Vector u1 = rotate(mover1.velocity, angle);
        Vector u2 = rotate(mover2.velocity, angle);

            // Velocity after 1d collision equation
        Vector v1 = new Vector( u1.x * (m1 - m2) / (m1 + m2) + u2.x * 2 * m2 / (m1 + m2), u1.y );
        Vector v2 = new Vector( u2.x * (m2 - m1) / (m1 + m2) + u1.x * 2 * m1 / (m1 + m2), u2.y );

            // Final velocity after rotating axis back to original location
        Vector vFinal1 = rotate(v1, -angle);
        Vector vFinal2 = rotate(v2, -angle);

            // Swap particle velocities for realistic bounce effect
            mover1.velocity.x = vFinal1.x;
            mover1.velocity.y = vFinal1.y;

            mover2.velocity.x = vFinal2.x;
            mover2.velocity.y = vFinal2.y;
        }
    }

    public void setColor(int i) {
        trackColor=i;
    }
}

