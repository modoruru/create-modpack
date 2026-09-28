package modoru.create.content.component.equipment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record AirState(int value, int maxValue, float temperature) {

    public static final Codec<AirState> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("value").forGetter(AirState::value),
                    Codec.INT.fieldOf("max_value").forGetter(AirState::maxValue),
                    Codec.FLOAT.fieldOf("temperature").forGetter(AirState::temperature)
            ).apply(instance, AirState::new)
    );

    public static final StreamCodec<ByteBuf, AirState> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull AirState decode(@NotNull ByteBuf byteBuf) {
            return new AirState(VarInt.read(byteBuf), VarInt.read(byteBuf), byteBuf.readFloat());
        }

        @Override
        public void encode(@NotNull ByteBuf o, @NotNull AirState airState) {
            VarInt.write(o, airState.value);
            VarInt.write(o, airState.maxValue);
            o.writeFloat(airState.temperature);
        }
    };

}
