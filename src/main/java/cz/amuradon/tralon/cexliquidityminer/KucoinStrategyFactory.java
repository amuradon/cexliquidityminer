package cz.amuradon.tralon.cexliquidityminer;

import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.kucoin.sdk.KucoinPrivateWSClient;
import com.kucoin.sdk.KucoinPublicWSClient;
import com.kucoin.sdk.KucoinRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class KucoinStrategyFactory {

	private final KucoinRestClient restClient;
    
    private final KucoinPublicWSClient wsClientPublic;
    
	private final KucoinPrivateWSClient wsClientPrivate;
	
	private final String baseToken;
	
	private final String quoteToken;
	
	private final ProducerTemplate producerTemplate;
	
	private final Map<String, Order> orders;
	
	private final OrderBookManager orderBookManager;
	
	@Inject
    public KucoinStrategyFactory(final KucoinRestClient restClient,
    		final KucoinPublicWSClient wsClientPublic,
    		final KucoinPrivateWSClient wsClientPrivate,
    		@ConfigProperty(name = "baseToken") String baseToken,
    		@ConfigProperty(name = "quoteToken") String quoteToken,
    		final ProducerTemplate producerTemplate,
    		final Map<String, Order> orders,
    		final OrderBookManager orderBookManager) {
		this.restClient = restClient;
		this.wsClientPublic = wsClientPublic;
		this.wsClientPrivate = wsClientPrivate;
		this.baseToken = baseToken;
		this.quoteToken = quoteToken;
		this.producerTemplate = producerTemplate;
		this.orders = orders;
		this.orderBookManager = orderBookManager;
    }
	
	public KucoinStrategy create() {
		return new KucoinStrategy(restClient, wsClientPublic, wsClientPrivate, baseToken, quoteToken,
				producerTemplate, orders, orderBookManager);
	}
}
