package barlapcb.frutagratis.View;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import barlapcb.frutagratis.R;

/**
 * Created by Daniel on 27/12/2016.
 */

public class ArvoreInfoWindow implements GoogleMap.InfoWindowAdapter {
    View view;

    public ArvoreInfoWindow(View view, String estadoFruta, String facilidadeColheita) {
        this.view = view;

        TextView textoEstadoFruta = (TextView) view.findViewById(R.id.mrk_estado_fruta);
        textoEstadoFruta.setText("Frutas: " + estadoFruta);

        TextView textoFacilidadeColheita = (TextView) view.findViewById(R.id.mrk_facilidade_colheita);
        textoEstadoFruta.setText("Facilidade de colheita: " + facilidadeColheita);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return view;
    }
}
