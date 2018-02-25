// CroneEngine_TestSine
// dumbest possible test: a single, mono sinewave
Engine_TestSine : CroneEngine {
	var <synth;

	*new { arg context, doneCallback;
		^super.new(context, doneCallback).init_TestSine;
	}

	init_TestSine {
		synth = {
			arg out=context.out_b, hz=220, amp=0.5, amplag=0.02, hzlag=0.01;
			var amp_, hz_;
			amp_ = Lag.ar(K2A.ar(amp), amplag);
			hz_ = Lag.ar(K2A.ar(hz), hzlag);
			Out.ar(out, (SinOsc.ar(hz_) * amp_).dup);
		}.play(context.xg);

		this.addCommand("hz", "f", { arg msg;
			synth.set(\hz, msg[1]);
		});

		this.addCommand("amp", "f", { arg msg;
			synth.set(\amp, msg[1]);
		});

		doneCallback.value(this);
	}

	free {
		synth.free;
		super.free;
	}
}
