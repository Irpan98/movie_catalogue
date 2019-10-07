package id.itborneo.moviecatalogue.widget2

import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class FavStackWidgetService : RemoteViewsService(){
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return FavStackRemoveFactory(this.applicationContext)
    }
    
}

